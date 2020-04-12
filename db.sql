--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

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
-- Name: sch1; Type: SCHEMA; Schema: -; Owner: dasha
--

CREATE SCHEMA sch1;


ALTER SCHEMA sch1 OWNER TO dasha;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: arr_hive; Type: TABLE; Schema: sch1; Owner: dasha
--

CREATE TABLE sch1.arr_hive (
    id character varying(100) NOT NULL,
    idcolumn integer,
    idrows integer
);


ALTER TABLE sch1.arr_hive OWNER TO dasha;

--
-- Name: columns; Type: TABLE; Schema: sch1; Owner: dasha
--

CREATE TABLE sch1.columns (
    id integer NOT NULL,
    namecol character varying(100),
    typecol integer
);


ALTER TABLE sch1.columns OWNER TO dasha;

--
-- Name: param; Type: TABLE; Schema: sch1; Owner: postgres
--

CREATE TABLE sch1.param (
    id integer NOT NULL,
    nameparam character varying(100)
);


ALTER TABLE sch1.param OWNER TO postgres;

--
-- Name: result; Type: TABLE; Schema: sch1; Owner: postgres
--

CREATE TABLE sch1.result (
    id integer NOT NULL
);


ALTER TABLE sch1.result OWNER TO postgres;

--
-- Name: rows; Type: TABLE; Schema: sch1; Owner: dasha
--

CREATE TABLE sch1.rows (
    id integer NOT NULL,
    numberrow integer
);


ALTER TABLE sch1.rows OWNER TO dasha;

--
-- Name: source_data; Type: TABLE; Schema: sch1; Owner: postgres
--

CREATE TABLE sch1.source_data (
    id integer NOT NULL,
    description character varying(100),
    count_rows integer,
    count_col integer,
    idtask integer,
    idarr_hive character varying(100)
);


ALTER TABLE sch1.source_data OWNER TO postgres;

--
-- Name: task; Type: TABLE; Schema: sch1; Owner: postgres
--

CREATE TABLE sch1.task (
    id integer NOT NULL,
    id_data integer,
    id_tests integer
);


ALTER TABLE sch1.task OWNER TO postgres;

--
-- Name: tests; Type: TABLE; Schema: sch1; Owner: postgres
--

CREATE TABLE sch1.tests (
    id integer NOT NULL,
    descriptoin character varying(100),
    idresult integer,
    idparam integer
);


ALTER TABLE sch1.tests OWNER TO postgres;

--
-- Name: types; Type: TABLE; Schema: sch1; Owner: dasha
--

CREATE TABLE sch1.types (
    id integer NOT NULL,
    nametype character varying(100)
);


ALTER TABLE sch1.types OWNER TO dasha;

--
-- Data for Name: arr_hive; Type: TABLE DATA; Schema: sch1; Owner: dasha
--

COPY sch1.arr_hive (id, idcolumn, idrows) FROM stdin;
\.


--
-- Data for Name: columns; Type: TABLE DATA; Schema: sch1; Owner: dasha
--

COPY sch1.columns (id, namecol, typecol) FROM stdin;
\.


--
-- Data for Name: param; Type: TABLE DATA; Schema: sch1; Owner: postgres
--

COPY sch1.param (id, nameparam) FROM stdin;
\.


--
-- Data for Name: result; Type: TABLE DATA; Schema: sch1; Owner: postgres
--

COPY sch1.result (id) FROM stdin;
\.


--
-- Data for Name: rows; Type: TABLE DATA; Schema: sch1; Owner: dasha
--

COPY sch1.rows (id, numberrow) FROM stdin;
\.


--
-- Data for Name: source_data; Type: TABLE DATA; Schema: sch1; Owner: postgres
--

COPY sch1.source_data (id, description, count_rows, count_col, idtask, idarr_hive) FROM stdin;
\.


--
-- Data for Name: task; Type: TABLE DATA; Schema: sch1; Owner: postgres
--

COPY sch1.task (id, id_data, id_tests) FROM stdin;
\.


--
-- Data for Name: tests; Type: TABLE DATA; Schema: sch1; Owner: postgres
--

COPY sch1.tests (id, descriptoin, idresult, idparam) FROM stdin;
\.


--
-- Data for Name: types; Type: TABLE DATA; Schema: sch1; Owner: dasha
--

COPY sch1.types (id, nametype) FROM stdin;
\.


--
-- Name: arr_hive arr_hive_pkey; Type: CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.arr_hive
    ADD CONSTRAINT arr_hive_pkey PRIMARY KEY (id);


--
-- Name: columns columns_pkey; Type: CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.columns
    ADD CONSTRAINT columns_pkey PRIMARY KEY (id);


--
-- Name: param param_pkey; Type: CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.param
    ADD CONSTRAINT param_pkey PRIMARY KEY (id);


--
-- Name: result result_pkey; Type: CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.result
    ADD CONSTRAINT result_pkey PRIMARY KEY (id);


--
-- Name: rows rows_pkey; Type: CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.rows
    ADD CONSTRAINT rows_pkey PRIMARY KEY (id);


--
-- Name: source_data source_data_pkey; Type: CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.source_data
    ADD CONSTRAINT source_data_pkey PRIMARY KEY (id);


--
-- Name: task task_pkey; Type: CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (id);


--
-- Name: tests tests_pkey; Type: CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.tests
    ADD CONSTRAINT tests_pkey PRIMARY KEY (id);


--
-- Name: types types_pkey; Type: CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.types
    ADD CONSTRAINT types_pkey PRIMARY KEY (id);


--
-- Name: arr_hive arr_hive_idcolumn_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.arr_hive
    ADD CONSTRAINT arr_hive_idcolumn_fkey FOREIGN KEY (idcolumn) REFERENCES sch1.columns(id);


--
-- Name: arr_hive arr_hive_idrows_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.arr_hive
    ADD CONSTRAINT arr_hive_idrows_fkey FOREIGN KEY (idrows) REFERENCES sch1.rows(id);


--
-- Name: columns columns_typecol_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: dasha
--

ALTER TABLE ONLY sch1.columns
    ADD CONSTRAINT columns_typecol_fkey FOREIGN KEY (typecol) REFERENCES sch1.types(id);


--
-- Name: source_data source_data_idarr_hive_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.source_data
    ADD CONSTRAINT source_data_idarr_hive_fkey FOREIGN KEY (idarr_hive) REFERENCES sch1.arr_hive(id);


--
-- Name: task task_id_data_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.task
    ADD CONSTRAINT task_id_data_fkey FOREIGN KEY (id_data) REFERENCES sch1.source_data(id);


--
-- Name: task task_id_tests_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.task
    ADD CONSTRAINT task_id_tests_fkey FOREIGN KEY (id_tests) REFERENCES sch1.tests(id);


--
-- Name: tests tests_idparam_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.tests
    ADD CONSTRAINT tests_idparam_fkey FOREIGN KEY (idparam) REFERENCES sch1.param(id);


--
-- Name: tests tests_idresult_fkey; Type: FK CONSTRAINT; Schema: sch1; Owner: postgres
--

ALTER TABLE ONLY sch1.tests
    ADD CONSTRAINT tests_idresult_fkey FOREIGN KEY (idresult) REFERENCES sch1.result(id);


--
-- PostgreSQL database dump complete
--

