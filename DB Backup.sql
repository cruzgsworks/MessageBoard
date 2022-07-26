--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-07-25 19:38:30

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
-- TOC entry 4 (class 2615 OID 17817)
-- Name: messageboard; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA messageboard;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 17991)
-- Name: member_access; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.member_access (
    access_id integer NOT NULL,
    is_admin boolean DEFAULT false NOT NULL,
    is_moderator boolean DEFAULT false NOT NULL,
    is_member boolean DEFAULT true NOT NULL,
    member_id integer NOT NULL
);


--
-- TOC entry 217 (class 1259 OID 17990)
-- Name: access_access_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: -
--

CREATE SEQUENCE messageboard.access_access_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 217
-- Name: access_access_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.access_access_id_seq OWNED BY messageboard.member_access.access_id;


--
-- TOC entry 212 (class 1259 OID 17827)
-- Name: board; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.board (
    board_id integer NOT NULL,
    board_name character varying(45) NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 17826)
-- Name: board_board_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: -
--

CREATE SEQUENCE messageboard.board_board_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 211
-- Name: board_board_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.board_board_id_seq OWNED BY messageboard.board.board_id;


--
-- TOC entry 214 (class 1259 OID 17868)
-- Name: messages; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.messages (
    message_id integer NOT NULL,
    message_timestamp timestamp without time zone NOT NULL,
    message_content text NOT NULL,
    board_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 17867)
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: -
--

CREATE SEQUENCE messageboard.board_messages_board_message_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 213
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.board_messages_board_message_id_seq OWNED BY messageboard.messages.message_id;


--
-- TOC entry 216 (class 1259 OID 17964)
-- Name: members; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.members (
    member_id integer NOT NULL,
    board_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 17963)
-- Name: members_member_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: -
--

CREATE SEQUENCE messageboard.members_member_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 215
-- Name: members_member_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.members_member_id_seq OWNED BY messageboard.members.member_id;


--
-- TOC entry 210 (class 1259 OID 17819)
-- Name: users; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.users (
    user_id integer NOT NULL,
    user_name character varying(45) NOT NULL,
    user_pass character varying(255) NOT NULL,
    user_first_name character varying(45) NOT NULL,
    user_last_name character varying(45) NOT NULL,
    user_email character varying(90) NOT NULL,
    user_auth_token character varying(32),
    user_auth_expiration timestamp without time zone,
    is_superadmin boolean DEFAULT false NOT NULL
);


--
-- TOC entry 209 (class 1259 OID 17818)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: -
--

CREATE SEQUENCE messageboard.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.users_user_id_seq OWNED BY messageboard.users.user_id;


--
-- TOC entry 3186 (class 2604 OID 17830)
-- Name: board board_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board ALTER COLUMN board_id SET DEFAULT nextval('messageboard.board_board_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 17994)
-- Name: member_access access_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access ALTER COLUMN access_id SET DEFAULT nextval('messageboard.access_access_id_seq'::regclass);


--
-- TOC entry 3188 (class 2604 OID 17967)
-- Name: members member_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members ALTER COLUMN member_id SET DEFAULT nextval('messageboard.members_member_id_seq'::regclass);


--
-- TOC entry 3187 (class 2604 OID 17871)
-- Name: messages message_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages ALTER COLUMN message_id SET DEFAULT nextval('messageboard.board_messages_board_message_id_seq'::regclass);


--
-- TOC entry 3184 (class 2604 OID 17822)
-- Name: users user_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users ALTER COLUMN user_id SET DEFAULT nextval('messageboard.users_user_id_seq'::regclass);


--
-- TOC entry 3355 (class 0 OID 17827)
-- Dependencies: 212
-- Data for Name: board; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3361 (class 0 OID 17991)
-- Dependencies: 218
-- Data for Name: member_access; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3359 (class 0 OID 17964)
-- Dependencies: 216
-- Data for Name: members; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3357 (class 0 OID 17868)
-- Dependencies: 214
-- Data for Name: messages; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3353 (class 0 OID 17819)
-- Dependencies: 210
-- Data for Name: users; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 217
-- Name: access_access_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.access_access_id_seq', 1, false);


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 211
-- Name: board_board_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.board_board_id_seq', 1, false);


--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 213
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.board_messages_board_message_id_seq', 1, false);


--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 215
-- Name: members_member_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.members_member_id_seq', 1, false);


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.users_user_id_seq', 1, false);


--
-- TOC entry 3207 (class 2606 OID 17999)
-- Name: member_access access_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_pk PRIMARY KEY (access_id);


--
-- TOC entry 3199 (class 2606 OID 18523)
-- Name: board board_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_name_un UNIQUE (board_name);


--
-- TOC entry 3201 (class 2606 OID 17832)
-- Name: board board_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_pk PRIMARY KEY (board_id);


--
-- TOC entry 3205 (class 2606 OID 17969)
-- Name: members members_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT members_pk PRIMARY KEY (member_id);


--
-- TOC entry 3203 (class 2606 OID 18020)
-- Name: messages message_id_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT message_id_pk PRIMARY KEY (message_id);


--
-- TOC entry 3194 (class 2606 OID 18041)
-- Name: users users_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_name_un UNIQUE (user_name);


--
-- TOC entry 3196 (class 2606 OID 17824)
-- Name: users users_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- TOC entry 3197 (class 1259 OID 18149)
-- Name: users_user_auth_token_idx; Type: INDEX; Schema: messageboard; Owner: -
--

CREATE INDEX users_user_auth_token_idx ON messageboard.users USING btree (user_auth_token);


--
-- TOC entry 3212 (class 2606 OID 26573)
-- Name: member_access access_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_fk FOREIGN KEY (member_id) REFERENCES messageboard.members(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 3212
-- Name: CONSTRAINT access_fk ON member_access; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT access_fk ON messageboard.member_access IS 'Has Access Level';


--
-- TOC entry 3211 (class 2606 OID 26522)
-- Name: members board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 3211
-- Name: CONSTRAINT board_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.members IS 'Has Members';


--
-- TOC entry 3208 (class 2606 OID 26532)
-- Name: messages board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 3208
-- Name: CONSTRAINT board_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.messages IS 'Has Messages';


--
-- TOC entry 3210 (class 2606 OID 26517)
-- Name: members user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 3210
-- Name: CONSTRAINT user_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.members IS 'Is a Member';


--
-- TOC entry 3209 (class 2606 OID 26542)
-- Name: messages user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 3209
-- Name: CONSTRAINT user_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.messages IS 'Has Messages';


-- Completed on 2022-07-25 19:38:30

--
-- PostgreSQL database dump complete
--

