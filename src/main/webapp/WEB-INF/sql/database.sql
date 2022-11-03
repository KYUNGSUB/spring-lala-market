-- 테이블스페이스 생성
create tablespace lalats
datafile 'E:\ksseo\dbms\oradata\ORCL\lalats.dbf' size 100M
autoextend on next 5M;

create tablespace lalats
datafile 'D:\oraclexe\oradata\ORCL\lalats.dbf' size 100M
autoextend on next 5M;

-- 사용자 생성 lala/lala
-- 에러 발생 시, 아래 설정을 먼저 실행을 해주어야 한다. 12c버전부터
alter session set "_ORACLE_SCRIPT"=true;
create user lala identified by lala
default tablespace lalats temporary tablespace temp;


-- 권한 부여를 해주어야 한다. : 접속, 자원을 사용할 수 있는 권한, select/insert 권한 부여
-- 지난 번에는 dba 권한을 부여하였지만, 일반사용자 권한을 부여해야 하므로 수정
grant connect, resource to lala;  -- 접속, 자원 사용에 대한 권한
alter user lala quota unlimited on lalats; -- 테이블스페이스 사용 권한

commit;

create table tbl_member (
	userid varchar2(50) not null primary key,
	userpw varchar2(100) not null,
	username varchar2(100) not null,
	regdate date default sysdate,
	updatedate date default sysdate,
	enabled char(1) default '1',
	mobile varchar2(15),
	email varchar2(20),
	marketing varchar2(2) default 'n',	-- 마케팅 정보 제공 동의
	rcv varchar2(2) default 'y',		-- 메일 수신 여부: 'n'(no), 'y'(yes)
	visited number default 0,			-- 방문 수
	pursuit number default 0,			-- 구매금액: 100원 단위
	accum number default 0				-- 적립금
);

create table tbl_member_auth (
	userid varchar2(50) not null,
	auth varchar2(50) not null,
	constraint fk_member_auth foreign key(userid) references tbl_member(userid)
);

create table persistent_logins (
	username varchar2(64) not null,	-- userid
	series varchar2(64) primary key,    -- 쿠키에 1:1대로 대응되는 값을 가진다.(쿠키를 가지고 사용자를 식별하는 series 생성)
	token varchar2(64) not null,		-- 세션에 대한 값을 저장
	last_used timestamp not null	-- 최종 로그인 시간 (T/O이 발생되는 시작시간)
);

create table terms (				-- 약관 관리 테이블
	tid number generated as identity primary key,	-- 아이디
	title varchar2(100) not null,	-- 제목
	content long not null,			-- 내용
	expose char(1) default '1',		-- 노출 여부 : true(사용), false(사용 안함)
	mandatory char(1) default '1',	-- 필수 여부 : true(필수), false(선택)
	createdAt date default sysdate,
	modifiedAt date default sysdate
);