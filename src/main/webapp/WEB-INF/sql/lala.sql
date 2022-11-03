drop table tbl_member_auth;
drop table tbl_member;

create table tbl_member (
	userid varchar2(50) not null primary key,
	userpw varchar2(100) not null,
	username varchar2(100) not null,
	regdate date default sysdate,
	updatedate date default sysdate,
	enabled char(1) default '1',
	mobile varchar2(15),
	email varchar2(20),
	marketing varchar2(2) default 'n',	-- 마�??�� ?���??? ?���??? ?��?��
	rcv varchar2(2) default 'y',		-- 메일 ?��?�� ?���???: 'n'(no), 'y'(yes)
	visited number default 0,			-- 방문 ?��
	pursuit number default 0,			-- 구매금액: 100?�� ?��?��
	accum number default 0				-- ?��립금
);

create table tbl_member_auth (
	userid varchar2(50) not null,
	auth varchar2(50) not null,
	constraint fk_member_auth foreign key(userid) references tbl_member(userid)
);

create table persistent_logins (
	username varchar2(64) not null,	-- userid
	series varchar2(64) primary key,    -- 쿠키?�� 1:1??�??? ???��?��?�� 값을 �???진다.(쿠키�??? �???�???�??? ?��?��?���??? ?��별하?�� series ?��?��)
	token varchar2(64) not null,		-- ?��?��?�� ???�� 값을 ???��
	last_used timestamp not null	-- 최종 로그?�� ?���??? (T/O?�� 발생?��?�� ?��?��?���???)
);

create table category (
	name varchar2(30) not null,
	code varchar2(20) not null primary key,
	expose char(1) default '1',
	gnb char(1) default '1',
	step integer not null,	-- 1(1�???) ?��?�� 2(2�???)
	seq integer not null,	-- ?��?�� 1~
	parent varchar2(20),	-- 1�??? ?��경우 null, 2�??? ?�� 경우 1차에 ???�� code ???��
	createdAt date default sysdate,
	modifiedAt date default sysdate
);

create table product (		-- ?��?��(product) ?��?���???
	pid number generated as identity,	-- ?��?�� ?��?��?��
	category1 varchar2(20) not null,	-- 1�??? ?��?�� 카테고리
	category2 varchar2(20) not null,	-- 2�??? ?��?�� 카테고리
	name varchar2(60) not null,		    -- ?��?�� ?���???
	price integer not null,				-- ?��?�� �???�???
	saleprice integer not null,			-- ?��매�?�???
	maxpurchase integer default 10,		-- 최�? 구매 �????�� ?��
	deposit integer default 0,			-- ?��립포?��?�� : -1(기본 ?��?��?��), 0(?��?��?�� ?��?��), >0(개별 ?��?��?�� %)
	delivery integer default 0,			-- 배송�??? : -1(기본 배송�???), 0(무료 배송), >0(개별 배송�???)
	newp char(1) default '1',		    -- ?��?�� ?��?�� : ?��?��?��
	best char(1) default '1',		    -- ?��?�� ?��?�� : Best
	discount char(1) default '1',		-- ?��?�� ?��?�� : ?��?�� 
	info char(1) default '1',			-- ?��?�� ?���??? : true(?��?��), false(미사?��)
	opt char(1) default '1',			-- ?��?�� : true(?��?��), false(미사?��)
	userid varchar2(10) not null,		-- ?��?��?��록자
	readcount integer default 0,		-- 조회?��
	introduction varchar2(100) not null,	-- ?��?�� ?���???
	pc_detail varchar2(4000) not null,		-- ?��?��?���???(PC)
	mobile_detail varchar2(4000) not null,	-- ?��?��?���???(Mobile)
	pc_delivery varchar2(4000),			-- 배송 ?��?��(PC) : null(공통), 그외(개별)
	mobile_delivery varchar2(4000),		-- 배송 ?��?��(Mobile) : null(공통), 그외(개별)
	pc_exchange varchar2(4000),			-- 교환 �??? 반품 ?��?��(PC) : null(공통), 그외(개별)
	mobile_exchange varchar2(4000),		-- 교환 �??? 반품 ?��?��(Mobile) : null(공통), 그외(개별)
	memo varchar2(300),			        -- ?��?�� 메모
	expose varchar2(10) not null,		-- ?��?�� ?���??? ?���??? : show(진열), out(?��?��), hide(?���???)
	createdAt date default sysdate,
	modifiedAt date default sysdate
);

alter table product add constraint pk_product primary key (pid);

create table product_info (		-- ?��?�� ?���??? ?��?���???
	pi_id number generated as identity,	-- ?��별자
	name varchar2(30) not null,			-- ?���??? �???
	description varchar2(60) not null,	-- ?���???
	pid number not null,				-- ?��?�� ?��?��?��
	createdAt date default sysdate,
	modifiedAt date default sysdate,
	foreign key (pid) references product(pid)
);

alter table product_info add constraint pk_product_info primary key (pi_id);

create table product_option (	-- ?��?�� ?��?�� ?��?���???
	po_id number generated as identity,	-- ?��별자
	gid integer not null,					-- 그룹 ID : ?��?�� 묶음
	name varchar2(30) not null,			-- ?��?���???
	description varchar2(60) not null,	-- ?���???
	price integer not null,					-- �???�???
	pid number not null,
	createdAt date default sysdate,
	modifiedAt date default sysdate,
	foreign key (pid) references product(pid)
);

alter table product_option add constraint pk_product_option primary key (po_id);

create table product_history (		-- ?��?�� ?��?�� ?��?���???
	hid number generated as identity,
	item integer not null,				-- 1(?���???), 2(?��?��), 3(?��?��)
	timeAt date default sysdate,	-- ?��?��/?���??? : yyyy-MM-dd HH:mm:ss
	userid varchar2(10) not null, 	-- �???리자 ?��?��?��
	pid number not null,
	foreign key (pid) references product(pid),
	foreign key (userid) references tbl_member(userid)
);

alter table product_history add constraint pk_product_history primary key (hid);

create table tbl_attach (
	uuid varchar2(100) not null,
	uploadPath varchar2(200) not null,
	fileName varchar2(100) not null,
	fileType varchar2(2) default '1',  --???? ?????? ?? ?? ??? ????
	bno number(10,0)
);

alter table tbl_attach add constraint pk_attach primary key (uuid);
-- alter table tbl_attach add constraint fk_board_attach foreign key (bno) references event (bno);

drop table product_history;
drop table product_info;
drop table product_option;
drop table product;

create table policy (
	code integer not null,			-- �ڵ� : �����Ϳ� ���� ������ ���� ������.
	category varchar2(60) not null,	-- �з� : ���Ǵ� ��ɿ�? ����
	name varchar2(100) not null,	-- �̸� : ���Ǵ� �Ķ����? ����(�̸����� ����)
	value varchar2(4000) not null,	-- �� : ������ �� (�������� ������ ������ ���� �� ������ ���ڿ��� ����)
	createdAt date default sysdate,
	modifiedAt date default sysdate
);

create table terms (					-- ?���? �?�? ?��?���?
	tid number generated as identity,	-- ?��?��?��
	title varchar2(100) not null,		-- ?���?
	content long not null,				-- ?��?��
	expose char(1) default '1',			-- ?���? ?���? : true(?��?��), false(?��?�� ?��?��)
	mandatory char(1) default '1',		-- ?��?�� ?���? : true(?��?��), false(?��?��)
	createdAt date default sysdate,
	modifiedAt date default sysdate
);

alter table terms add constraint pk_terms primary key (tid);

/*
 * ��� ����(kind) : 1(GNB), 2(����), 3(��Ÿ�ϼ� ����Ʈ), 4(���¼� ����Ʈ),
 * 		5(���� �޴�), 6(Ŀ�´�Ƽ ����Ʈ), 7(������), 8(��ǰ �ֹ� �Ϸ�)
 * ��� ��ġ(position) : 1(���), 2(������), 3(����), 4(�ϴ�)
 * ������(location) : 1(�����̴�), 2(����), 3(�α��� ��/��), 4(�������� ����)
 */
create table banner (
	bid integer generated as identity,
	kind integer not null,
	position integer not null,
	location integer not null,
	createdAt date default sysdate,
	modifiedAt date default sysdate
);

alter table banner add constraint pk_banner primary key (bid);

-- ��� ������ ��� ��ġ�� ���� ��ʸ� ����
create index idx_banner on banner(kind, position);

create table banner_info (
	info_id integer generated as identity,
	bid integer not null,
	url varchar2(100),
	alt varchar2(100),
	target varchar2(10),
	loginBefore integer default 0,
	createdAt date default sysdate,
	modifiedAt date default sysdate,
	foreign key (bid) references banner(bid)
);

alter table banner_info add constraint pk_banner_info primary key (info_id);

create table notice (
	nid number generated as identity,
	title varchar2(100) not null,
	content varchar2(4000) not null,
	important char(1) default '1',
	regDate date default sysdate,
	modDate date default sysdate
);

alter table notice add constraint pk_notice primary key (nid);

commit;