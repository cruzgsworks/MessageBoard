--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-07-15 17:34:47

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
-- Name: messageboard; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA messageboard;


ALTER SCHEMA messageboard OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 17991)
-- Name: member_access; Type: TABLE; Schema: messageboard; Owner: postgres
--

CREATE TABLE messageboard.member_access (
    access_id integer NOT NULL,
    is_admin boolean DEFAULT false NOT NULL,
    is_moderator boolean DEFAULT false NOT NULL,
    is_member boolean DEFAULT true NOT NULL,
    member_id integer NOT NULL
);


ALTER TABLE messageboard.member_access OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17990)
-- Name: access_access_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: postgres
--

CREATE SEQUENCE messageboard.access_access_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messageboard.access_access_id_seq OWNER TO postgres;

--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 217
-- Name: access_access_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: postgres
--

ALTER SEQUENCE messageboard.access_access_id_seq OWNED BY messageboard.member_access.access_id;


--
-- TOC entry 212 (class 1259 OID 17827)
-- Name: board; Type: TABLE; Schema: messageboard; Owner: postgres
--

CREATE TABLE messageboard.board (
    board_id integer NOT NULL,
    board_name character varying(45) NOT NULL
);


ALTER TABLE messageboard.board OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 17826)
-- Name: board_board_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: postgres
--

CREATE SEQUENCE messageboard.board_board_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messageboard.board_board_id_seq OWNER TO postgres;

--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 211
-- Name: board_board_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: postgres
--

ALTER SEQUENCE messageboard.board_board_id_seq OWNED BY messageboard.board.board_id;


--
-- TOC entry 214 (class 1259 OID 17868)
-- Name: messages; Type: TABLE; Schema: messageboard; Owner: postgres
--

CREATE TABLE messageboard.messages (
    message_id integer NOT NULL,
    message_timestamp timestamp without time zone NOT NULL,
    message_content text NOT NULL,
    board_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE messageboard.messages OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 17867)
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: postgres
--

CREATE SEQUENCE messageboard.board_messages_board_message_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messageboard.board_messages_board_message_id_seq OWNER TO postgres;

--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 213
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: postgres
--

ALTER SEQUENCE messageboard.board_messages_board_message_id_seq OWNED BY messageboard.messages.message_id;


--
-- TOC entry 216 (class 1259 OID 17964)
-- Name: members; Type: TABLE; Schema: messageboard; Owner: postgres
--

CREATE TABLE messageboard.members (
    member_id integer NOT NULL,
    board_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE messageboard.members OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 17963)
-- Name: members_member_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: postgres
--

CREATE SEQUENCE messageboard.members_member_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messageboard.members_member_id_seq OWNER TO postgres;

--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 215
-- Name: members_member_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: postgres
--

ALTER SEQUENCE messageboard.members_member_id_seq OWNED BY messageboard.members.member_id;


--
-- TOC entry 210 (class 1259 OID 17819)
-- Name: users; Type: TABLE; Schema: messageboard; Owner: postgres
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


ALTER TABLE messageboard.users OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 17818)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: messageboard; Owner: postgres
--

CREATE SEQUENCE messageboard.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messageboard.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: messageboard; Owner: postgres
--

ALTER SEQUENCE messageboard.users_user_id_seq OWNED BY messageboard.users.user_id;


--
-- TOC entry 3186 (class 2604 OID 17830)
-- Name: board board_id; Type: DEFAULT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.board ALTER COLUMN board_id SET DEFAULT nextval('messageboard.board_board_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 17994)
-- Name: member_access access_id; Type: DEFAULT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.member_access ALTER COLUMN access_id SET DEFAULT nextval('messageboard.access_access_id_seq'::regclass);


--
-- TOC entry 3188 (class 2604 OID 17967)
-- Name: members member_id; Type: DEFAULT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.members ALTER COLUMN member_id SET DEFAULT nextval('messageboard.members_member_id_seq'::regclass);


--
-- TOC entry 3187 (class 2604 OID 17871)
-- Name: messages message_id; Type: DEFAULT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.messages ALTER COLUMN message_id SET DEFAULT nextval('messageboard.board_messages_board_message_id_seq'::regclass);


--
-- TOC entry 3184 (class 2604 OID 17822)
-- Name: users user_id; Type: DEFAULT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.users ALTER COLUMN user_id SET DEFAULT nextval('messageboard.users_user_id_seq'::regclass);


--
-- TOC entry 3355 (class 0 OID 17827)
-- Dependencies: 212
-- Data for Name: board; Type: TABLE DATA; Schema: messageboard; Owner: postgres
--

COPY messageboard.board (board_id, board_name) FROM stdin;
1	Apple
2	Alphabet
\.


--
-- TOC entry 3361 (class 0 OID 17991)
-- Dependencies: 218
-- Data for Name: member_access; Type: TABLE DATA; Schema: messageboard; Owner: postgres
--

COPY messageboard.member_access (access_id, is_admin, is_moderator, is_member, member_id) FROM stdin;
1	t	f	f	1
2	t	f	f	2
4	f	f	t	4
5	f	t	f	5
\.


--
-- TOC entry 3359 (class 0 OID 17964)
-- Dependencies: 216
-- Data for Name: members; Type: TABLE DATA; Schema: messageboard; Owner: postgres
--

COPY messageboard.members (member_id, board_id, user_id) FROM stdin;
1	1	3
2	2	3
4	1	4
5	1	5
\.


--
-- TOC entry 3357 (class 0 OID 17868)
-- Dependencies: 214
-- Data for Name: messages; Type: TABLE DATA; Schema: messageboard; Owner: postgres
--

COPY messageboard.messages (message_id, message_timestamp, message_content, board_id, user_id) FROM stdin;
\.


--
-- TOC entry 3353 (class 0 OID 17819)
-- Dependencies: 210
-- Data for Name: users; Type: TABLE DATA; Schema: messageboard; Owner: postgres
--

COPY messageboard.users (user_id, user_name, user_pass, user_first_name, user_last_name, user_email, user_auth_token, user_auth_expiration, is_superadmin) FROM stdin;
2	lukeskywalker	USioGcwu83NBltY8eg5L8F4NeyRYJdYB4uMcqzrIETA=	Luke	Skywalker	lukeskywalker@email.com	\N	\N	t
4	maryjane	me8hMe0PufzuOEw0Vc1jiBNz6hSVG7Ot	Mary	Jane	maryjane@email.com	7li2xIzA6m61ISrKaGPc0qpvzVUSfpnD	2022-07-16 12:20:53.116	f
3	johndoe	wTTbdT6c1VNR5W5pGFZYBNDCDa6ADun3	John	Doe	johndoe@email.com	KH9FT0FV41f4oVpQnc3av1mKLlmsGUFW	2022-07-16 12:22:30.901	f
5	nikolatesla	1wVN+mBWVajHA+7UO+do+O0UPEfkqWam	Nikola	Tesla	nikolatesla@email.com	VV8OIHgaXNYio6SLVyBAWV7w0i27lOaF	2022-07-16 12:22:55.433	f
1	gerardcruz	QU54GuBiMx/v5FAD4hD+RHqbyD9EkuGW	Gerard	Cruz	gerard984@revature.net	eyaajlEW6nlsWQ2dvytj9jiWC9WptcLA	2022-07-16 12:42:06.827	t
\.


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 217
-- Name: access_access_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: postgres
--

SELECT pg_catalog.setval('messageboard.access_access_id_seq', 5, true);


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 211
-- Name: board_board_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: postgres
--

SELECT pg_catalog.setval('messageboard.board_board_id_seq', 3, true);


--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 213
-- Name: board_messages_board_message_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: postgres
--

SELECT pg_catalog.setval('messageboard.board_messages_board_message_id_seq', 1, false);


--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 215
-- Name: members_member_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: postgres
--

SELECT pg_catalog.setval('messageboard.members_member_id_seq', 5, true);


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: messageboard; Owner: postgres
--

SELECT pg_catalog.setval('messageboard.users_user_id_seq', 6, true);


--
-- TOC entry 3207 (class 2606 OID 17999)
-- Name: member_access access_pk; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_pk PRIMARY KEY (access_id);


--
-- TOC entry 3199 (class 2606 OID 18523)
-- Name: board board_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_name_un UNIQUE (board_name);


--
-- TOC entry 3201 (class 2606 OID 17832)
-- Name: board board_pk; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.board
    ADD CONSTRAINT board_pk PRIMARY KEY (board_id);


--
-- TOC entry 3205 (class 2606 OID 17969)
-- Name: members members_pk; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT members_pk PRIMARY KEY (member_id);


--
-- TOC entry 3203 (class 2606 OID 18020)
-- Name: messages message_id_pk; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT message_id_pk PRIMARY KEY (message_id);


--
-- TOC entry 3194 (class 2606 OID 18041)
-- Name: users users_name_un; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_name_un UNIQUE (user_name);


--
-- TOC entry 3196 (class 2606 OID 17824)
-- Name: users users_pk; Type: CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- TOC entry 3197 (class 1259 OID 18149)
-- Name: users_user_auth_token_idx; Type: INDEX; Schema: messageboard; Owner: postgres
--

CREATE INDEX users_user_auth_token_idx ON messageboard.users USING btree (user_auth_token);


--
-- TOC entry 3212 (class 2606 OID 26573)
-- Name: member_access access_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.member_access
    ADD CONSTRAINT access_fk FOREIGN KEY (member_id) REFERENCES messageboard.members(member_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 3212
-- Name: CONSTRAINT access_fk ON member_access; Type: COMMENT; Schema: messageboard; Owner: postgres
--

COMMENT ON CONSTRAINT access_fk ON messageboard.member_access IS 'Has Access Level';


--
-- TOC entry 3211 (class 2606 OID 26522)
-- Name: members board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 3211
-- Name: CONSTRAINT board_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: postgres
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.members IS 'Has Members';


--
-- TOC entry 3208 (class 2606 OID 26532)
-- Name: messages board_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES messageboard.board(board_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 3208
-- Name: CONSTRAINT board_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: postgres
--

COMMENT ON CONSTRAINT board_id_fk ON messageboard.messages IS 'Has Messages';


--
-- TOC entry 3210 (class 2606 OID 26517)
-- Name: members user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.members
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 3210
-- Name: CONSTRAINT user_id_fk ON members; Type: COMMENT; Schema: messageboard; Owner: postgres
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.members IS 'Is a Member';


--
-- TOC entry 3209 (class 2606 OID 26542)
-- Name: messages user_id_fk; Type: FK CONSTRAINT; Schema: messageboard; Owner: postgres
--

ALTER TABLE ONLY messageboard.messages
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES messageboard.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 3209
-- Name: CONSTRAINT user_id_fk ON messages; Type: COMMENT; Schema: messageboard; Owner: postgres
--

COMMENT ON CONSTRAINT user_id_fk ON messageboard.messages IS 'Has Messages';


-- Completed on 2022-07-15 17:34:47

--
-- PostgreSQL database dump complete
--

