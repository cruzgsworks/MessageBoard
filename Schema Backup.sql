--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-08-02 13:04:16

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
-- TOC entry 5 (class 2615 OID 36878)
-- Name: messageboard; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA messageboard;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 237 (class 1259 OID 36879)
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
-- TOC entry 238 (class 1259 OID 36885)
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
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 238
-- Name: access_access_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.access_access_id_seq OWNED BY messageboard.member_access.access_id;


--
-- TOC entry 239 (class 1259 OID 36886)
-- Name: board; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.board (
    board_id integer NOT NULL,
    board_name character varying(45) NOT NULL
);


--
-- TOC entry 240 (class 1259 OID 36889)
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
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 240
-- Name: board_board_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.board_board_id_seq OWNED BY messageboard.board.board_id;


--
-- TOC entry 241 (class 1259 OID 36890)
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
-- TOC entry 242 (class 1259 OID 36895)
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
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 242
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.board_messages_board_message_id_seq OWNED BY messageboard.messages.message_id;


--
-- TOC entry 243 (class 1259 OID 36896)
-- Name: members; Type: TABLE; Schema: messageboard; Owner: -
--

CREATE TABLE messageboard.members (
    member_id integer NOT NULL,
    board_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 244 (class 1259 OID 36899)
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
-- TOC entry 3418 (class 0 OID 0)
-- Dependencies: 244
-- Name: members_member_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.members_member_id_seq OWNED BY messageboard.members.member_id;


--
-- TOC entry 245 (class 1259 OID 36900)
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
-- TOC entry 246 (class 1259 OID 36906)
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
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 246
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: -
--

ALTER SEQUENCE messageboard.users_user_id_seq OWNED BY messageboard.users.user_id;


--
-- TOC entry 3236 (class 2604 OID 36907)
-- Name: board board_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board ALTER COLUMN board_id SET DEFAULT nextval('messageboard.board_board_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 36908)
-- Name: member_access access_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access ALTER COLUMN access_id SET DEFAULT nextval('messageboard.access_access_id_seq'::regclass);


--
-- TOC entry 3238 (class 2604 OID 36909)
-- Name: members member_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members ALTER COLUMN member_id SET DEFAULT nextval('messageboard.members_member_id_seq'::regclass);


--
-- TOC entry 3237 (class 2604 OID 36910)
-- Name: messages message_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages ALTER COLUMN message_id SET DEFAULT nextval('messageboard.board_messages_board_message_id_seq'::regclass);


--
-- TOC entry 3240 (class 2604 OID 36911)
-- Name: users user_id; Type: DEFAULT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users ALTER COLUMN user_id SET DEFAULT nextval('messageboard.users_user_id_seq'::regclass);


--
-- TOC entry 3402 (class 0 OID 36886)
-- Dependencies: 239
-- Data for Name: board; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3400 (class 0 OID 36879)
-- Dependencies: 237
-- Data for Name: member_access; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3406 (class 0 OID 36896)
-- Dependencies: 243
-- Data for Name: members; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3404 (class 0 OID 36890)
-- Dependencies: 241
-- Data for Name: messages; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3408 (class 0 OID 36900)
-- Dependencies: 245
-- Data for Name: users; Type: TABLE DATA; Schema: messageboard; Owner: -
--



--
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 238
-- Name: access_access_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.access_access_id_seq', 1, false);


--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 240
-- Name: board_board_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.board_board_id_seq', 1, false);


--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 242
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.board_messages_board_message_id_seq', 1, false);


--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 244
-- Name: members_member_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.members_member_id_seq', 1, false);


--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 246
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: -
--

SELECT pg_catalog.setval('messageboard.users_user_id_seq', 1, false);


--
-- TOC entry 3242 (class 2606 OID 36913)
-- Name: member_access access_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_pk PRIMARY KEY (access_id);


--
-- TOC entry 3244 (class 2606 OID 36915)
-- Name: board board_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_name_un UNIQUE (board_name);


--
-- TOC entry 3246 (class 2606 OID 36917)
-- Name: board board_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_pk PRIMARY KEY (board_id);


--
-- TOC entry 3250 (class 2606 OID 36919)
-- Name: members members_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT members_pk PRIMARY KEY (member_id);


--
-- TOC entry 3248 (class 2606 OID 36921)
-- Name: messages message_id_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT message_id_pk PRIMARY KEY (message_id);


--
-- TOC entry 3252 (class 2606 OID 36923)
-- Name: users users_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_name_un UNIQUE (user_name);


--
-- TOC entry 3254 (class 2606 OID 36925)
-- Name: users users_pk; Type: CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- TOC entry 3255 (class 1259 OID 36926)
-- Name: users_user_auth_token_idx; Type: INDEX; Schema: messageboard; Owner: -
--

CREATE INDEX users_user_auth_token_idx ON messageboard.users USING btree (user_auth_token);


--
-- TOC entry 3256 (class 2606 OID 36927)
-- Name: member_access access_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_fk FOREIGN KEY (member_id) REFERENCES messageboard.members(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 3256
-- Name: CONSTRAINT access_fk ON member_access; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT access_fk ON messageboard.member_access IS 'Has Access Level';


--
-- TOC entry 3259 (class 2606 OID 36932)
-- Name: members board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 3259
-- Name: CONSTRAINT board_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.members IS 'Has Members';


--
-- TOC entry 3257 (class 2606 OID 36937)
-- Name: messages board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 3257
-- Name: CONSTRAINT board_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.messages IS 'Has Messages';


--
-- TOC entry 3260 (class 2606 OID 36942)
-- Name: members user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 3260
-- Name: CONSTRAINT user_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.members IS 'Is a Member';


--
-- TOC entry 3258 (class 2606 OID 36947)
-- Name: messages user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: -
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 3258
-- Name: CONSTRAINT user_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: -
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.messages IS 'Has Messages';


-- Completed on 2022-08-02 13:04:16

--
-- PostgreSQL database dump complete
--

