--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

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
-- Name: before_insert_animator(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_animator() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.employee_id=nextval('animator_id_sequence');
new.given_password=(random()* (999999-100000 + 1) + 100000);
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_animator() OWNER TO postgres;

--
-- Name: before_insert_create_individual_activity(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_create_individual_activity() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.individual_activity_id=nextval('individual_activity_sequence');
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_create_individual_activity() OWNER TO postgres;

--
-- Name: before_insert_create_mass_activity(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_create_mass_activity() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.mass_activity_id=nextval('mass_activity_sequence');
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_create_mass_activity() OWNER TO postgres;

--
-- Name: before_insert_customer(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_customer() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.customer_password=(random()* (999999-100000 + 1) + 100000);
--update customerinformation set customer_password=1234 where vacation_village_id=new.vacation_village_id;
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_customer() OWNER TO postgres;

--
-- Name: before_insert_customer_information(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_customer_information() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.vacation_village_id=nextval('customer_id_sequence');
new.given_password=(random()* (999999-100000 + 1) + 100000);
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_customer_information() OWNER TO postgres;

--
-- Name: before_insert_equip_person_information(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.before_insert_equip_person_information() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
new.ssn=nextval('equipperson_id_sequence');
RETURN NEW;
END;
$$;


ALTER FUNCTION public.before_insert_equip_person_information() OWNER TO postgres;

--
-- Name: individual_activity_deleted(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.individual_activity_deleted() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ 
	  BEGIN 
      DELETE FROM emergencyinformation WHERE activity_id = OLD.individual_activity_id; 
      RETURN NEW;
      END;
      $$;


ALTER FUNCTION public.individual_activity_deleted() OWNER TO postgres;

--
-- Name: mass_activity_deleted(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mass_activity_deleted() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ 
	  BEGIN 
      DELETE FROM emergencyinformation WHERE activity_id = OLD.mass_activity_id; 
      RETURN NEW;
      END;
      $$;


ALTER FUNCTION public.mass_activity_deleted() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: addequipment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addequipment (
    equipname character varying(30),
    purpose character varying(50),
    equip_person_ssn integer
);


ALTER TABLE public.addequipment OWNER TO postgres;

--
-- Name: animator_id_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.animator_id_sequence
    START WITH 20000
    INCREMENT BY 1
    MINVALUE 20000
    MAXVALUE 99999
    CACHE 3;


ALTER TABLE public.animator_id_sequence OWNER TO postgres;

--
-- Name: animatorinformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.animatorinformation (
    employee_id integer NOT NULL,
    animator_name character varying(25),
    phone_number character(10),
    expertise_area character varying(20),
    given_password integer
);


ALTER TABLE public.animatorinformation OWNER TO postgres;

--
-- Name: createdindiviualactivity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.createdindiviualactivity (
    individual_activity_name character varying(15) NOT NULL,
    internet_link character varying(100),
    age_limit integer,
    individual_activity_id integer NOT NULL
);


ALTER TABLE public.createdindiviualactivity OWNER TO postgres;

--
-- Name: createdmassactivity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.createdmassactivity (
    mass_activity_name character varying(15),
    internet_link character varying(100),
    capacity integer,
    mass_activity_id integer NOT NULL
);


ALTER TABLE public.createdmassactivity OWNER TO postgres;

--
-- Name: customer_id_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_id_sequence
    START WITH 10000
    INCREMENT BY 1
    MINVALUE 10000
    MAXVALUE 99999
    CACHE 10;


ALTER TABLE public.customer_id_sequence OWNER TO postgres;

--
-- Name: customerinformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customerinformation (
    vacation_village_id integer NOT NULL,
    customer_name character varying(25),
    age integer,
    room_number integer,
    contact_phone character(10),
    given_password integer
);


ALTER TABLE public.customerinformation OWNER TO postgres;

--
-- Name: emergencyinformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emergencyinformation (
    activity_id integer NOT NULL,
    phone_number character(10),
    emergencyinfo character varying(200),
    lockernumber integer
);


ALTER TABLE public.emergencyinformation OWNER TO postgres;

--
-- Name: equipperson_id_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.equipperson_id_sequence
    START WITH 30000
    INCREMENT BY 1
    MINVALUE 30000
    MAXVALUE 99999
    CACHE 10;


ALTER TABLE public.equipperson_id_sequence OWNER TO postgres;

--
-- Name: equippersoninformation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.equippersoninformation (
    ssn integer NOT NULL,
    equip_person_name character varying(25),
    equip_person_surname character varying(25),
    contact_phone character(10)
);


ALTER TABLE public.equippersoninformation OWNER TO postgres;

--
-- Name: individual_activity_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.individual_activity_sequence
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    MAXVALUE 9999
    CACHE 5;


ALTER TABLE public.individual_activity_sequence OWNER TO postgres;

--
-- Name: individualactivityagelimit; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.individualactivityagelimit AS
 SELECT createdindiviualactivity.age_limit
   FROM public.createdindiviualactivity;


ALTER TABLE public.individualactivityagelimit OWNER TO postgres;

--
-- Name: individualactivitynames; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.individualactivitynames AS
 SELECT createdindiviualactivity.individual_activity_name
   FROM public.createdindiviualactivity;


ALTER TABLE public.individualactivitynames OWNER TO postgres;

--
-- Name: indivudualactivityappointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.indivudualactivityappointment (
    customer_id integer NOT NULL,
    activity_id integer NOT NULL,
    animator_id integer NOT NULL,
    activity_date character varying(15) NOT NULL,
    activity_hour character varying(15) NOT NULL
);


ALTER TABLE public.indivudualactivityappointment OWNER TO postgres;

--
-- Name: individualactivityselectedcount; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.individualactivityselectedcount AS
 SELECT indivudualactivityappointment.activity_id,
    indivudualactivityappointment.activity_date,
    indivudualactivityappointment.activity_hour,
    count(indivudualactivityappointment.activity_id) AS count
   FROM public.indivudualactivityappointment
  GROUP BY indivudualactivityappointment.activity_id, indivudualactivityappointment.activity_date, indivudualactivityappointment.activity_hour;


ALTER TABLE public.individualactivityselectedcount OWNER TO postgres;

--
-- Name: mass_activity_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mass_activity_sequence
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mass_activity_sequence OWNER TO postgres;

--
-- Name: massactivityappointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.massactivityappointment (
    customer_id integer NOT NULL,
    activity_id integer NOT NULL,
    animator_id integer NOT NULL,
    activity_date character varying(15) NOT NULL,
    activity_hour character varying(15) NOT NULL
);


ALTER TABLE public.massactivityappointment OWNER TO postgres;

--
-- Name: massactivitycapacity; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.massactivitycapacity AS
 SELECT createdmassactivity.capacity
   FROM public.createdmassactivity;


ALTER TABLE public.massactivitycapacity OWNER TO postgres;

--
-- Name: massactivitynames; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.massactivitynames AS
 SELECT createdmassactivity.mass_activity_name
   FROM public.createdmassactivity;


ALTER TABLE public.massactivitynames OWNER TO postgres;

--
-- Name: massactivityselectedcount; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.massactivityselectedcount AS
 SELECT massactivityappointment.activity_id,
    massactivityappointment.activity_date,
    massactivityappointment.activity_hour,
    count(massactivityappointment.activity_id) AS count
   FROM public.massactivityappointment
  GROUP BY massactivityappointment.activity_id, massactivityappointment.activity_date, massactivityappointment.activity_hour;


ALTER TABLE public.massactivityselectedcount OWNER TO postgres;

--
-- Name: passwordsequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passwordsequence
    START WITH 100000
    INCREMENT BY 11
    MINVALUE 100000
    MAXVALUE 999999
    CACHE 20;


ALTER TABLE public.passwordsequence OWNER TO postgres;

--
-- Data for Name: addequipment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.addequipment (equipname, purpose, equip_person_ssn) FROM stdin;
dumbbell	for the gym	20031
\.


--
-- Data for Name: animatorinformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.animatorinformation (employee_id, animator_name, phone_number, expertise_area, given_password) FROM stdin;
20030	arda	1234567890	dance	195920
20031	bora	1234567899	fitness	220094
20033	beril	1212121212	pilates	135522
\.


--
-- Data for Name: createdindiviualactivity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.createdindiviualactivity (individual_activity_name, internet_link, age_limit, individual_activity_id) FROM stdin;
\.


--
-- Data for Name: createdmassactivity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.createdmassactivity (mass_activity_name, internet_link, capacity, mass_activity_id) FROM stdin;
pilates	http:/pilates.com	6	53
\.


--
-- Data for Name: customerinformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customerinformation (vacation_village_id, customer_name, age, room_number, contact_phone, given_password) FROM stdin;
10130	elif	58	244	5327213492	253848
10131	yaren	21	230	1234567888	797350
\.


--
-- Data for Name: emergencyinformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emergencyinformation (activity_id, phone_number, emergencyinfo, lockernumber) FROM stdin;
\.


--
-- Data for Name: equippersoninformation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.equippersoninformation (ssn, equip_person_name, equip_person_surname, contact_phone) FROM stdin;
30000	bora	araz	1111111111
\.


--
-- Data for Name: indivudualactivityappointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.indivudualactivityappointment (customer_id, activity_id, animator_id, activity_date, activity_hour) FROM stdin;
10131	115	20033	11-05-2022	11:00-11:50
10130	115	20033	11-05-2022	12:00-12:50
\.


--
-- Data for Name: massactivityappointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.massactivityappointment (customer_id, activity_id, animator_id, activity_date, activity_hour) FROM stdin;
10130	53	20033	11-05-2022	11:00-11:50
10131	53	20033	11-05-2022	11:00-11:50
\.


--
-- Name: animator_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.animator_id_sequence', 20035, true);


--
-- Name: customer_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_id_sequence', 10139, true);


--
-- Name: equipperson_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.equipperson_id_sequence', 30009, true);


--
-- Name: individual_activity_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.individual_activity_sequence', 124, true);


--
-- Name: mass_activity_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mass_activity_sequence', 53, true);


--
-- Name: passwordsequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.passwordsequence', 114069, true);


--
-- Name: animatorinformation animatorinformation_phone_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.animatorinformation
    ADD CONSTRAINT animatorinformation_phone_number_key UNIQUE (phone_number);


--
-- Name: animatorinformation animatorinformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.animatorinformation
    ADD CONSTRAINT animatorinformation_pkey PRIMARY KEY (employee_id);


--
-- Name: createdindiviualactivity createdindiviualactivity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.createdindiviualactivity
    ADD CONSTRAINT createdindiviualactivity_pkey PRIMARY KEY (individual_activity_name, individual_activity_id);


--
-- Name: createdmassactivity createdmassactivity_mass_activity_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.createdmassactivity
    ADD CONSTRAINT createdmassactivity_mass_activity_name_key UNIQUE (mass_activity_name);


--
-- Name: createdmassactivity createdmassactivity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.createdmassactivity
    ADD CONSTRAINT createdmassactivity_pkey PRIMARY KEY (mass_activity_id);


--
-- Name: customerinformation customerinformation_contact_phone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customerinformation
    ADD CONSTRAINT customerinformation_contact_phone_key UNIQUE (contact_phone);


--
-- Name: customerinformation customerinformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customerinformation
    ADD CONSTRAINT customerinformation_pkey PRIMARY KEY (vacation_village_id);


--
-- Name: emergencyinformation emergencyinformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergencyinformation
    ADD CONSTRAINT emergencyinformation_pkey PRIMARY KEY (activity_id);


--
-- Name: equippersoninformation equippersoninformation_contact_phone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equippersoninformation
    ADD CONSTRAINT equippersoninformation_contact_phone_key UNIQUE (contact_phone);


--
-- Name: equippersoninformation equippersoninformation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equippersoninformation
    ADD CONSTRAINT equippersoninformation_pkey PRIMARY KEY (ssn);


--
-- Name: indivudualactivityappointment indivudualactivityappointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.indivudualactivityappointment
    ADD CONSTRAINT indivudualactivityappointment_pkey PRIMARY KEY (customer_id, activity_id, animator_id, activity_date, activity_hour);


--
-- Name: massactivityappointment massactivityappointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.massactivityappointment
    ADD CONSTRAINT massactivityappointment_pkey PRIMARY KEY (customer_id, activity_id, animator_id, activity_date, activity_hour);


--
-- Name: createdindiviualactivity individual_activity_delete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER individual_activity_delete AFTER DELETE ON public.createdindiviualactivity FOR EACH ROW EXECUTE FUNCTION public.individual_activity_deleted();


--
-- Name: customerinformation insertcreatedcustomerinformation; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertcreatedcustomerinformation BEFORE INSERT ON public.customerinformation FOR EACH ROW EXECUTE FUNCTION public.before_insert_customer_information();


--
-- Name: equippersoninformation insertcreatedequippersoninformation; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertcreatedequippersoninformation BEFORE INSERT ON public.equippersoninformation FOR EACH ROW EXECUTE FUNCTION public.before_insert_equip_person_information();


--
-- Name: createdindiviualactivity insertcreatedindividualactivity; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertcreatedindividualactivity BEFORE INSERT ON public.createdindiviualactivity FOR EACH ROW EXECUTE FUNCTION public.before_insert_create_individual_activity();


--
-- Name: createdmassactivity insertcreatedmassactivity; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertcreatedmassactivity BEFORE INSERT ON public.createdmassactivity FOR EACH ROW EXECUTE FUNCTION public.before_insert_create_mass_activity();


--
-- Name: animatorinformation insertpasswordanimator; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertpasswordanimator BEFORE INSERT ON public.animatorinformation FOR EACH ROW EXECUTE FUNCTION public.before_insert_animator();


--
-- Name: createdmassactivity mass_activity_delete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER mass_activity_delete AFTER DELETE ON public.createdmassactivity FOR EACH ROW EXECUTE FUNCTION public.mass_activity_deleted();


--
-- Name: massactivityappointment massactivityappointment_activity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.massactivityappointment
    ADD CONSTRAINT massactivityappointment_activity_id_fkey FOREIGN KEY (activity_id) REFERENCES public.createdmassactivity(mass_activity_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: massactivityappointment massactivityappointment_animator_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.massactivityappointment
    ADD CONSTRAINT massactivityappointment_animator_id_fkey FOREIGN KEY (animator_id) REFERENCES public.animatorinformation(employee_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: massactivityappointment massactivityappointment_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.massactivityappointment
    ADD CONSTRAINT massactivityappointment_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customerinformation(vacation_village_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

