--
-- PostgreSQL database cluster dump
--

-- Started on 2024-10-29 12:10:14

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;
CREATE ROLE sgc;
ALTER ROLE sgc WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;
CREATE ROLE sgc2;
ALTER ROLE sgc2 WITH NOSUPERUSER NOINHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE sgcapp;
ALTER ROLE sgcapp WITH NOSUPERUSER NOINHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE sgcscheduler;
ALTER ROLE sgcscheduler WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE sgs;
ALTER ROLE sgs WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;
CREATE ROLE wm;
ALTER ROLE wm WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;






--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10
-- Dumped by pg_dump version 14.10

-- Started on 2024-10-29 12:10:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2024-10-29 12:10:14

--
-- PostgreSQL database dump complete
--

--
-- Database "lojavirtual" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10
-- Dumped by pg_dump version 14.10

-- Started on 2024-10-29 12:10:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3531 (class 1262 OID 1760772)
-- Name: lojavirtual; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE lojavirtual WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE lojavirtual OWNER TO postgres;

\connect lojavirtual

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 249 (class 1255 OID 1761012)
-- Name: validatepersonkey(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatepersonkey() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

  declare existe integer;

  begin
    existe = (select count(1) from individual_person where id = NEW.person_id);
    if(existe <=0 ) then
     existe = (select count(1) from entity_person where id = NEW.person_id);
    if (existe <= 0) then
      raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
     end if;
    end if;
    RETURN NEW;
  end;
  $$;


ALTER FUNCTION public.validatepersonkey() OWNER TO postgres;

--
-- TOC entry 250 (class 1255 OID 1761023)
-- Name: validatepersonkey2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatepersonkey2() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

  declare existe integer;

  begin
    existe = (select count(1) from individual_person where id = NEW.supplier_person_id);
    if(existe <=0 ) then
     existe = (select count(1) from entity_person where id = NEW.supplier_person_id);
    if (existe <= 0) then
      raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
     end if;
    end if;
    RETURN NEW;
  end;
  $$;


ALTER FUNCTION public.validatepersonkey2() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 1760773)
-- Name: access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.access (
    id bigint NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE public.access OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 1760778)
-- Name: account_payable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_payable (
    id bigint NOT NULL,
    account_payables_status character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    discount_value numeric(38,2),
    due_date date NOT NULL,
    payment_date date,
    total_value numeric(38,2) NOT NULL,
    supplier_person_id bigint NOT NULL,
    person_id bigint NOT NULL,
    CONSTRAINT account_payable_account_payables_status_check CHECK (((account_payables_status)::text = ANY ((ARRAY['CHARGE'::character varying, 'OVERDUE'::character varying, 'OPENED'::character varying, 'PAID'::character varying, 'NEGOTIATED'::character varying])::text[])))
);


ALTER TABLE public.account_payable OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 1760786)
-- Name: account_receivable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_receivable (
    id bigint NOT NULL,
    account_receivable_type character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    discount_value numeric(38,2),
    due_date date NOT NULL,
    payment_date date,
    total_value numeric(38,2) NOT NULL,
    person_id bigint NOT NULL,
    CONSTRAINT account_receivable_account_receivable_type_check CHECK (((account_receivable_type)::text = ANY ((ARRAY['CHARGE'::character varying, 'OVERDUE'::character varying, 'OPENED'::character varying, 'PAID'::character varying])::text[])))
);


ALTER TABLE public.account_receivable OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 1760794)
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
    id bigint NOT NULL,
    address_type character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    complement character varying(255),
    neighborhood character varying(255) NOT NULL,
    number character varying(255) NOT NULL,
    state character varying(255) NOT NULL,
    street character varying(255) NOT NULL,
    person_id bigint NOT NULL,
    CONSTRAINT address_address_type_check CHECK (((address_type)::text = ANY ((ARRAY['COLLECTION'::character varying, 'DELIVERY'::character varying])::text[])))
);


ALTER TABLE public.address OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 1760802)
-- Name: brand_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.brand_product (
    id bigint NOT NULL,
    name_description character varying(255) NOT NULL
);


ALTER TABLE public.brand_product OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 1760807)
-- Name: discount_coupon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discount_coupon (
    id bigint NOT NULL,
    code_description character varying(255) NOT NULL,
    discount_value numeric(38,2),
    expiration_date date NOT NULL,
    porcentage_discount numeric(38,2)
);


ALTER TABLE public.discount_coupon OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 1760812)
-- Name: entity_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entity_person (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    category character varying(255),
    cnpj character varying(255) NOT NULL,
    company_name character varying(255) NOT NULL,
    fantasy_name character varying(255) NOT NULL,
    municipal_registration character varying(255),
    state_registration character varying(255) NOT NULL
);


ALTER TABLE public.entity_person OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 1760819)
-- Name: individual_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.individual_person (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    birth_date date,
    cpf character varying(255) NOT NULL
);


ALTER TABLE public.individual_person OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 1760826)
-- Name: invoice_item_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice_item_product (
    id bigint NOT NULL,
    quantity double precision NOT NULL,
    product_id bigint NOT NULL,
    purchase_invoice_id bigint NOT NULL
);


ALTER TABLE public.invoice_item_product OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 1760831)
-- Name: payment_form; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payment_form (
    id bigint NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE public.payment_form OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 1760836)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    sale_price numeric(38,2) NOT NULL,
    unity_type character varying(255) NOT NULL,
    active boolean,
    alert_quantity_stock integer,
    alert_quantity_stock_enabled boolean,
    click_count integer,
    depth double precision NOT NULL,
    description text NOT NULL,
    height double precision NOT NULL,
    link_youtube character varying(255),
    name character varying(255) NOT NULL,
    stock_quantity_stock integer NOT NULL,
    weight double precision NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 1760843)
-- Name: product_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_category (
    id bigint NOT NULL,
    name_description character varying(255) NOT NULL
);


ALTER TABLE public.product_category OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 1760848)
-- Name: product_image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_image (
    id bigint NOT NULL,
    original_image text NOT NULL,
    small_image text NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public.product_image OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 1760855)
-- Name: product_review; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_review (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    rating integer NOT NULL,
    person_id bigint NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public.product_review OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 1760860)
-- Name: purchase_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_invoice (
    id bigint NOT NULL,
    description character varying(255),
    icmsvalue numeric(38,2) NOT NULL,
    discount_value numeric(38,2),
    invoice_number character varying(255) NOT NULL,
    invoice_series character varying(255) NOT NULL,
    purchase_date date NOT NULL,
    total_value numeric(38,2) NOT NULL,
    account_payable_id bigint NOT NULL,
    person_id bigint NOT NULL
);


ALTER TABLE public.purchase_invoice OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 1760867)
-- Name: sale_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sale_item (
    id bigint NOT NULL,
    quantity double precision NOT NULL,
    product_id bigint NOT NULL,
    sales_invoice_id bigint NOT NULL
);


ALTER TABLE public.sale_item OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 1760872)
-- Name: sales_and_buy_online_store; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_and_buy_online_store (
    id bigint NOT NULL,
    delivery_date date NOT NULL,
    discount_value numeric(38,2),
    sales_date date NOT NULL,
    shipping_days integer NOT NULL,
    shipping_value numeric(38,2) NOT NULL,
    total_value numeric(38,2) NOT NULL,
    billing_address_id bigint NOT NULL,
    delivery_address_id bigint NOT NULL,
    discount_coupon_id bigint,
    payment_form_id bigint NOT NULL,
    person_id bigint NOT NULL,
    sales_invoice_id bigint NOT NULL
);


ALTER TABLE public.sales_and_buy_online_store OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 1760877)
-- Name: sales_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_invoice (
    id bigint NOT NULL,
    invoice_number character varying(255) NOT NULL,
    invoice_series character varying(255) NOT NULL,
    invoice_type character varying(255) NOT NULL,
    pdf text NOT NULL,
    xml text NOT NULL,
    sales_and_buy_online_store_id bigint NOT NULL
);


ALTER TABLE public.sales_invoice OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 1760909)
-- Name: seq_access; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_access
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_access OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 1760910)
-- Name: seq_account_payable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_account_payable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_account_payable OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 1760911)
-- Name: seq_account_receivable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_account_receivable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_account_receivable OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 1760912)
-- Name: seq_address; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_address
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_address OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 1760913)
-- Name: seq_brand_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_brand_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_brand_product OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 1760914)
-- Name: seq_discount_coupon; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_discount_coupon
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_discount_coupon OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 1760915)
-- Name: seq_invoice_item_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_invoice_item_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_invoice_item_product OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 1760916)
-- Name: seq_payment_form; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_payment_form
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_payment_form OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 1760917)
-- Name: seq_person; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_person
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_person OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 1760918)
-- Name: seq_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 1760919)
-- Name: seq_product_category; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_category
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_category OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 1760920)
-- Name: seq_product_image; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_image
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_image OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 1760921)
-- Name: seq_product_review; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_review
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_review OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 1760922)
-- Name: seq_purchase_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_purchase_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_purchase_invoice OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 1760923)
-- Name: seq_sale_item; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sale_item
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_sale_item OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 1760924)
-- Name: seq_sales_and_buy_online_store; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sales_and_buy_online_store
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_sales_and_buy_online_store OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 1760925)
-- Name: seq_sales_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sales_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_sales_invoice OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 1760926)
-- Name: seq_tracking_status; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_tracking_status
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_tracking_status OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 1760927)
-- Name: seq_users; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_users
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_users OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 1760884)
-- Name: tracking_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tracking_status (
    id bigint NOT NULL,
    city character varying(255),
    distribution_center character varying(255),
    state character varying(255),
    status character varying(255),
    sales_and_buy_online_store_id bigint NOT NULL
);


ALTER TABLE public.tracking_status OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 1760891)
-- Name: user_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_access (
    user_id bigint NOT NULL,
    access_id bigint NOT NULL
);


ALTER TABLE public.user_access OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 1760894)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    actual_date_password date NOT NULL,
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    person_id bigint NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3486 (class 0 OID 1760773)
-- Dependencies: 209
-- Data for Name: access; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.access (id, description) FROM stdin;
\.


--
-- TOC entry 3487 (class 0 OID 1760778)
-- Dependencies: 210
-- Data for Name: account_payable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_payable (id, account_payables_status, description, discount_value, due_date, payment_date, total_value, supplier_person_id, person_id) FROM stdin;
\.


--
-- TOC entry 3488 (class 0 OID 1760786)
-- Dependencies: 211
-- Data for Name: account_receivable; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_receivable (id, account_receivable_type, description, discount_value, due_date, payment_date, total_value, person_id) FROM stdin;
\.


--
-- TOC entry 3489 (class 0 OID 1760794)
-- Dependencies: 212
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.address (id, address_type, cep, city, complement, neighborhood, number, state, street, person_id) FROM stdin;
\.


--
-- TOC entry 3490 (class 0 OID 1760802)
-- Dependencies: 213
-- Data for Name: brand_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.brand_product (id, name_description) FROM stdin;
\.


--
-- TOC entry 3491 (class 0 OID 1760807)
-- Dependencies: 214
-- Data for Name: discount_coupon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.discount_coupon (id, code_description, discount_value, expiration_date, porcentage_discount) FROM stdin;
\.


--
-- TOC entry 3492 (class 0 OID 1760812)
-- Dependencies: 215
-- Data for Name: entity_person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entity_person (id, email, name, phone, category, cnpj, company_name, fantasy_name, municipal_registration, state_registration) FROM stdin;
\.


--
-- TOC entry 3493 (class 0 OID 1760819)
-- Dependencies: 216
-- Data for Name: individual_person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.individual_person (id, email, name, phone, birth_date, cpf) FROM stdin;
1	mailton@junior.com	Mailton Junior	3899999-9999	2024-10-10	11111111111
\.


--
-- TOC entry 3494 (class 0 OID 1760826)
-- Dependencies: 217
-- Data for Name: invoice_item_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.invoice_item_product (id, quantity, product_id, purchase_invoice_id) FROM stdin;
\.


--
-- TOC entry 3495 (class 0 OID 1760831)
-- Dependencies: 218
-- Data for Name: payment_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.payment_form (id, description) FROM stdin;
\.


--
-- TOC entry 3496 (class 0 OID 1760836)
-- Dependencies: 219
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id, sale_price, unity_type, active, alert_quantity_stock, alert_quantity_stock_enabled, click_count, depth, description, height, link_youtube, name, stock_quantity_stock, weight) FROM stdin;
1	10.00	teste	t	1	t	0	0	teste	1	no	teste	1	1
\.


--
-- TOC entry 3497 (class 0 OID 1760843)
-- Dependencies: 220
-- Data for Name: product_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_category (id, name_description) FROM stdin;
\.


--
-- TOC entry 3498 (class 0 OID 1760848)
-- Dependencies: 221
-- Data for Name: product_image; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_image (id, original_image, small_image, product_id) FROM stdin;
\.


--
-- TOC entry 3499 (class 0 OID 1760855)
-- Dependencies: 222
-- Data for Name: product_review; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_review (id, description, rating, person_id, product_id) FROM stdin;
1	testevalidar	10	1	1
2	testevalidar	10	1	1
\.


--
-- TOC entry 3500 (class 0 OID 1760860)
-- Dependencies: 223
-- Data for Name: purchase_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchase_invoice (id, description, icmsvalue, discount_value, invoice_number, invoice_series, purchase_date, total_value, account_payable_id, person_id) FROM stdin;
\.


--
-- TOC entry 3501 (class 0 OID 1760867)
-- Dependencies: 224
-- Data for Name: sale_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sale_item (id, quantity, product_id, sales_invoice_id) FROM stdin;
\.


--
-- TOC entry 3502 (class 0 OID 1760872)
-- Dependencies: 225
-- Data for Name: sales_and_buy_online_store; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sales_and_buy_online_store (id, delivery_date, discount_value, sales_date, shipping_days, shipping_value, total_value, billing_address_id, delivery_address_id, discount_coupon_id, payment_form_id, person_id, sales_invoice_id) FROM stdin;
\.


--
-- TOC entry 3503 (class 0 OID 1760877)
-- Dependencies: 226
-- Data for Name: sales_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sales_invoice (id, invoice_number, invoice_series, invoice_type, pdf, xml, sales_and_buy_online_store_id) FROM stdin;
\.


--
-- TOC entry 3504 (class 0 OID 1760884)
-- Dependencies: 227
-- Data for Name: tracking_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tracking_status (id, city, distribution_center, state, status, sales_and_buy_online_store_id) FROM stdin;
\.


--
-- TOC entry 3505 (class 0 OID 1760891)
-- Dependencies: 228
-- Data for Name: user_access; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_access (user_id, access_id) FROM stdin;
\.


--
-- TOC entry 3506 (class 0 OID 1760894)
-- Dependencies: 229
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, actual_date_password, login, password, person_id) FROM stdin;
\.


--
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 230
-- Name: seq_access; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_access', 1, false);


--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 231
-- Name: seq_account_payable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_account_payable', 1, false);


--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_account_receivable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_account_receivable', 1, false);


--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 233
-- Name: seq_address; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_address', 1, false);


--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 234
-- Name: seq_brand_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_brand_product', 1, false);


--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 235
-- Name: seq_discount_coupon; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_discount_coupon', 1, false);


--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 236
-- Name: seq_invoice_item_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_invoice_item_product', 1, false);


--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 237
-- Name: seq_payment_form; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_payment_form', 1, false);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 238
-- Name: seq_person; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_person', 1, false);


--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 239
-- Name: seq_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product', 1, false);


--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 240
-- Name: seq_product_category; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_category', 1, false);


--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 241
-- Name: seq_product_image; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_image', 1, false);


--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 242
-- Name: seq_product_review; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_review', 1, false);


--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 243
-- Name: seq_purchase_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_purchase_invoice', 1, false);


--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 244
-- Name: seq_sale_item; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_sale_item', 1, false);


--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 245
-- Name: seq_sales_and_buy_online_store; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_sales_and_buy_online_store', 1, false);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 246
-- Name: seq_sales_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_sales_invoice', 1, false);


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 247
-- Name: seq_tracking_status; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_tracking_status', 1, false);


--
-- TOC entry 3550 (class 0 OID 0)
-- Dependencies: 248
-- Name: seq_users; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_users', 1, false);


--
-- TOC entry 3268 (class 2606 OID 1760777)
-- Name: access access_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.access
    ADD CONSTRAINT access_pkey PRIMARY KEY (id);


--
-- TOC entry 3270 (class 2606 OID 1760785)
-- Name: account_payable account_payable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_payable
    ADD CONSTRAINT account_payable_pkey PRIMARY KEY (id);


--
-- TOC entry 3272 (class 2606 OID 1760793)
-- Name: account_receivable account_receivable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_receivable
    ADD CONSTRAINT account_receivable_pkey PRIMARY KEY (id);


--
-- TOC entry 3274 (class 2606 OID 1760801)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- TOC entry 3276 (class 2606 OID 1760806)
-- Name: brand_product brand_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.brand_product
    ADD CONSTRAINT brand_product_pkey PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 1760811)
-- Name: discount_coupon discount_coupon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupon
    ADD CONSTRAINT discount_coupon_pkey PRIMARY KEY (id);


--
-- TOC entry 3280 (class 2606 OID 1760818)
-- Name: entity_person entity_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entity_person
    ADD CONSTRAINT entity_person_pkey PRIMARY KEY (id);


--
-- TOC entry 3282 (class 2606 OID 1760825)
-- Name: individual_person individual_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individual_person
    ADD CONSTRAINT individual_person_pkey PRIMARY KEY (id);


--
-- TOC entry 3284 (class 2606 OID 1760830)
-- Name: invoice_item_product invoice_item_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT invoice_item_product_pkey PRIMARY KEY (id);


--
-- TOC entry 3286 (class 2606 OID 1760835)
-- Name: payment_form payment_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_form
    ADD CONSTRAINT payment_form_pkey PRIMARY KEY (id);


--
-- TOC entry 3290 (class 2606 OID 1760847)
-- Name: product_category product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- TOC entry 3292 (class 2606 OID 1760854)
-- Name: product_image product_image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT product_image_pkey PRIMARY KEY (id);


--
-- TOC entry 3288 (class 2606 OID 1760842)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3294 (class 2606 OID 1760859)
-- Name: product_review product_review_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT product_review_pkey PRIMARY KEY (id);


--
-- TOC entry 3296 (class 2606 OID 1760866)
-- Name: purchase_invoice purchase_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT purchase_invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 3298 (class 2606 OID 1760871)
-- Name: sale_item sale_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_item
    ADD CONSTRAINT sale_item_pkey PRIMARY KEY (id);


--
-- TOC entry 3300 (class 2606 OID 1760876)
-- Name: sales_and_buy_online_store sales_and_buy_online_store_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT sales_and_buy_online_store_pkey PRIMARY KEY (id);


--
-- TOC entry 3304 (class 2606 OID 1760883)
-- Name: sales_invoice sales_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_invoice
    ADD CONSTRAINT sales_invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 3308 (class 2606 OID 1760890)
-- Name: tracking_status tracking_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT tracking_status_pkey PRIMARY KEY (id);


--
-- TOC entry 3310 (class 2606 OID 1760908)
-- Name: user_access uk_ojlpsp4dq6pt966i85jb7i386; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT uk_ojlpsp4dq6pt966i85jb7i386 UNIQUE (access_id);


--
-- TOC entry 3306 (class 2606 OID 1760904)
-- Name: sales_invoice uk_otu74mp4ijfipy0vnn6774nxb; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_invoice
    ADD CONSTRAINT uk_otu74mp4ijfipy0vnn6774nxb UNIQUE (sales_and_buy_online_store_id);


--
-- TOC entry 3302 (class 2606 OID 1760902)
-- Name: sales_and_buy_online_store uk_s2esw88x8hs8fptn5oh1cn03l; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT uk_s2esw88x8hs8fptn5oh1cn03l UNIQUE (sales_invoice_id);


--
-- TOC entry 3312 (class 2606 OID 1760906)
-- Name: user_access uk_user_access; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT uk_user_access UNIQUE (user_id, access_id);


--
-- TOC entry 3314 (class 2606 OID 1760900)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3334 (class 2620 OID 1761021)
-- Name: account_payable validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.account_payable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3336 (class 2620 OID 1761026)
-- Name: account_receivable validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.account_receivable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3338 (class 2620 OID 1761028)
-- Name: address validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.address FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3342 (class 2620 OID 1761030)
-- Name: purchase_invoice validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.purchase_invoice FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3343 (class 2620 OID 1761034)
-- Name: sales_and_buy_online_store validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.sales_and_buy_online_store FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3346 (class 2620 OID 1761032)
-- Name: users validatepersonkeyaccount_payable; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3333 (class 2620 OID 1761022)
-- Name: account_payable validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.account_payable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3335 (class 2620 OID 1761027)
-- Name: account_receivable validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.account_receivable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3337 (class 2620 OID 1761029)
-- Name: address validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.address FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3341 (class 2620 OID 1761031)
-- Name: purchase_invoice validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.purchase_invoice FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3344 (class 2620 OID 1761035)
-- Name: sales_and_buy_online_store validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.sales_and_buy_online_store FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3345 (class 2620 OID 1761033)
-- Name: users validatepersonkeyaccount_payable2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payable2 BEFORE INSERT ON public.users FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3331 (class 2620 OID 1761025)
-- Name: account_payable validatepersonkeyaccount_payablesupplier_person_id; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payablesupplier_person_id BEFORE INSERT ON public.account_payable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey2();


--
-- TOC entry 3332 (class 2620 OID 1761024)
-- Name: account_payable validatepersonkeyaccount_payablsupplier_person_id; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaccount_payablsupplier_person_id BEFORE UPDATE ON public.account_payable FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey2();


--
-- TOC entry 3340 (class 2620 OID 1761013)
-- Name: product_review validatepersonkeyproductreview; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyproductreview BEFORE UPDATE ON public.product_review FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3339 (class 2620 OID 1761020)
-- Name: product_review validatepersonkeyproductreview2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyproductreview2 BEFORE INSERT ON public.product_review FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- TOC entry 3315 (class 2606 OID 1760928)
-- Name: invoice_item_product fk_invoice_item_product_product; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT fk_invoice_item_product_product FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3316 (class 2606 OID 1760933)
-- Name: invoice_item_product fk_invoice_item_product_purchase_invoice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT fk_invoice_item_product_purchase_invoice FOREIGN KEY (purchase_invoice_id) REFERENCES public.purchase_invoice(id);


--
-- TOC entry 3317 (class 2606 OID 1760938)
-- Name: product_image fk_product_image_product; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT fk_product_image_product FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3318 (class 2606 OID 1760943)
-- Name: product_review fk_product_review_product; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT fk_product_review_product FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3319 (class 2606 OID 1760948)
-- Name: purchase_invoice fk_purchase_invoice_account_payable; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT fk_purchase_invoice_account_payable FOREIGN KEY (account_payable_id) REFERENCES public.account_payable(id);


--
-- TOC entry 3320 (class 2606 OID 1760953)
-- Name: sale_item fk_sale_item_product; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_item
    ADD CONSTRAINT fk_sale_item_product FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3321 (class 2606 OID 1760958)
-- Name: sale_item fk_sale_item_sales_invoice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sale_item
    ADD CONSTRAINT fk_sale_item_sales_invoice FOREIGN KEY (sales_invoice_id) REFERENCES public.sales_and_buy_online_store(id);


--
-- TOC entry 3322 (class 2606 OID 1760963)
-- Name: sales_and_buy_online_store fk_sales_and_buy_online_store_billing_address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT fk_sales_and_buy_online_store_billing_address FOREIGN KEY (billing_address_id) REFERENCES public.address(id);


--
-- TOC entry 3323 (class 2606 OID 1760968)
-- Name: sales_and_buy_online_store fk_sales_and_buy_online_store_delivery_address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT fk_sales_and_buy_online_store_delivery_address FOREIGN KEY (delivery_address_id) REFERENCES public.address(id);


--
-- TOC entry 3324 (class 2606 OID 1760973)
-- Name: sales_and_buy_online_store fk_sales_and_buy_online_store_discount_coupon; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT fk_sales_and_buy_online_store_discount_coupon FOREIGN KEY (discount_coupon_id) REFERENCES public.discount_coupon(id);


--
-- TOC entry 3325 (class 2606 OID 1760978)
-- Name: sales_and_buy_online_store fk_sales_and_buy_online_store_payment_form; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT fk_sales_and_buy_online_store_payment_form FOREIGN KEY (payment_form_id) REFERENCES public.payment_form(id);


--
-- TOC entry 3326 (class 2606 OID 1760983)
-- Name: sales_and_buy_online_store fk_sales_and_buy_online_store_sales_invoice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_and_buy_online_store
    ADD CONSTRAINT fk_sales_and_buy_online_store_sales_invoice FOREIGN KEY (sales_invoice_id) REFERENCES public.sales_invoice(id);


--
-- TOC entry 3327 (class 2606 OID 1760988)
-- Name: sales_invoice fk_sales_invoice_sales_and_buy_online_store; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_invoice
    ADD CONSTRAINT fk_sales_invoice_sales_and_buy_online_store FOREIGN KEY (sales_and_buy_online_store_id) REFERENCES public.sales_and_buy_online_store(id);


--
-- TOC entry 3328 (class 2606 OID 1760993)
-- Name: tracking_status fk_tracking_status_sales_and_buy_online_store; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT fk_tracking_status_sales_and_buy_online_store FOREIGN KEY (sales_and_buy_online_store_id) REFERENCES public.sales_and_buy_online_store(id);


--
-- TOC entry 3329 (class 2606 OID 1760998)
-- Name: user_access fk_user_access_access; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT fk_user_access_access FOREIGN KEY (access_id) REFERENCES public.access(id);


--
-- TOC entry 3330 (class 2606 OID 1761003)
-- Name: user_access fk_user_access_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT fk_user_access_user FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2024-10-29 12:10:15

--
-- PostgreSQL database dump complete
--

-- Completed on 2024-10-29 12:10:15

--
-- PostgreSQL database cluster dump complete
--

