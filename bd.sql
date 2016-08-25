-- Table: buyer

-- DROP TABLE buyer;

CREATE SEQUENCE products_id_seq START 1;
CREATE SEQUENCE sail_id_seq START 1;
CREATE SEQUENCE buyer_id_seq START 1;
CREATE SEQUENCE buyer_info_id_seq START 1;
CREATE SEQUENCE pic_id_seq START 1;
CREATE SEQUENCE user_id_seq1 START 1;
CREATE SEQUENCE sold_id_seq START 1;

CREATE TABLE user_shop
(
  id bigint NOT NULL DEFAULT nextval('user_id_seq1'::regclass),
  name text,
  password text,
  enable boolean,
  role_id bigint,
  CONSTRAINT user_pkey1 PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


-- Index: fki_role

-- DROP INDEX fki_role;

CREATE TABLE buyer
(

  name text,
  password text,
  buyer_id bigint NOT NULL DEFAULT nextval('buyer_id_seq'::regclass),
  enable boolean,
  reg_date timestamp without time zone,
  CONSTRAINT user_pkey PRIMARY KEY (buyer_id)
)
WITH (
  OIDS=FALSE
);


-- DROP TABLE buyer_info;

CREATE TABLE buyer_info
(
  second_name text,
  age integer,
  phone text,
  info_id bigserial NOT NULL,
  ava_path text,
  CONSTRAINT buyer_info_pkey PRIMARY KEY (info_id)
)
WITH (
  OIDS=FALSE
); 
  -- Table: product

-- DROP TABLE product;

CREATE TABLE product
(
  product_id bigint NOT NULL DEFAULT nextval('products_id_seq'::regclass),
  name text,
  cost double precision,
  CONSTRAINT prim PRIMARY KEY (product_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product
  OWNER TO postgres;


  
  -- Table: sail

-- DROP TABLE sail;

CREATE TABLE sail
(
  sail_id bigint NOT NULL DEFAULT nextval('sail_id_seq'::regclass),
  amount integer,
  date date,
  totalsum double precision,
  CONSTRAINT "prim key" PRIMARY KEY (sail_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sail
  OWNER TO postgres;

  
  -- Table: sail_by_buyer

-- DROP TABLE sail_by_buyer;

CREATE TABLE sail_by_buyer
(
  sail_id bigint,
  buyer_id bigint
)
WITH (
  OIDS=FALSE
);


CREATE TABLE picture_product
(
  pic_id bigint NOT NULL DEFAULT nextval('pic_id_seq'::regclass),
  path text,
  product_id bigint,
  
  CONSTRAINT picture_product_pkey PRIMARY KEY (pic_id)
  
)
WITH (
  OIDS=FALSE
);

CREATE TABLE chat
(
  id bigserial NOT NULL,
  recipient bigint,
  sender bigint,
  text text,
  date timestamp without time zone,
  CONSTRAINT chat_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chat
  OWNER TO postgres;

  CREATE TABLE discount
(
  id bigserial NOT NULL,
  discount smallint,
  product_id bigint,
  buyer_id bigint,
  active boolean,
  CONSTRAINT discount_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE discount
  OWNER TO postgres;
  
  CREATE TABLE settings
(
  name text,
  value text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE settings
  OWNER TO postgres;


CREATE TABLE user_role
(
  role text,
  role_id bigserial NOT NULL,
  info boolean,
  add_sail boolean,
  CONSTRAINT user_role_pkey PRIMARY KEY (role_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_role
  OWNER TO postgres;
  
  CREATE TABLE total_product_report
(
  id bigserial NOT NULL,
  product_id bigint,
  total_amount integer,
  total_cost double precision,
  CONSTRAINT total_product_report_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE total_product_report
  OWNER TO postgres;
  
CREATE TABLE sold_product
(
  id bigserial NOT NULL,
  name text,
  amount bigint,
  cost double precision,
  sail_id bigint,
  discount smallint,
  CONSTRAINT sold_product_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

  
  
  
  CREATE INDEX fki_role
  ON user_shop
  USING btree
  (role_id);
  
  CREATE INDEX "fki_SA"
  ON picture_product
  USING btree
  (product_id);

  
 ALTER TABLE sold_product
  OWNER TO postgres,
  ADD FOREIGN KEY (sail_id) REFERENCES sail (sail_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE buyer_info
  OWNER TO postgres,
   ADD FOREIGN KEY (info_id) REFERENCES buyer (buyer_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
  
  ALTER TABLE sail_by_buyer
  OWNER TO postgres,
  ADD FOREIGN KEY (sail_id) REFERENCES sail (sail_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  ADD FOREIGN KEY (buyer_id) REFERENCES buyer (buyer_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
  
  ALTER TABLE picture_product
  OWNER TO postgres,
  ADD FOREIGN KEY (product_id) REFERENCES product (product_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
  
  ALTER TABLE user_shop
  OWNER TO postgres,
  ADD FOREIGN KEY (role_id) REFERENCES user_role (role_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

INSERT INTO user_role(role, role_id, info, add_sail) VALUES ('ROLE_ADMIN', 1,true,true);
INSERT INTO user_role(role, role_id, info, add_sail) VALUES ('ROLE_USER', 2,true,true);
  
INSERT INTO user_shop(name, password, enable, role_id) VALUES ('admin', md5('password'), true, 1);   

INSERT INTO settings(name, value) VALUES ('time_general_discount', '300000');
INSERT INTO settings(name, value) VALUES ('time_disactive_discount', '600000');
INSERT INTO settings(name, value) VALUES ('path_upload_avatar', 'NEED PATH IN resources');
INSERT INTO settings(name, value) VALUES ('path_upload_pic_product', 'NEED PATH IN resources');
INSERT INTO discount(discount, active) VALUES (50, true);

INSERT INTO settings(name, value) VALUES ('base_cashback', '50');
INSERT INTO settings(name, value) VALUES ('auto_delivered_complete', '300');



  