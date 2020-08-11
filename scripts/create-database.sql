
create table usuario (
	id integer,
	nome varchar,
	constraint pk_usuario primary key(id)
);

create table tweet (
	id integer,
	id_usuario integer,
	conteudo varchar,
	data_postagem timestamp,
	constraint pk_tweet primary key(id),
	constraint fk_tweet_01 foreign key (id_usuario) references usuario(id) on delete restrict
);

create table comentario (
	id integer,
	id_usuario integer,
	id_tweet integer,
	conteudo varchar,
	data_postagem timestamp,
	constraint pk_comentario primary key(id),
	constraint fk_comentario_01 foreign key (id_usuario) references usuario(id) on delete restrict,
	constraint fk_comentario_02 foreign key (id_tweet) references tweet(id) on delete cascade
);

create sequence seq_usuario start with 1 increment by 1;
create sequence seq_tweet start with 1 increment by 1;
create sequence seq_comentario start with 1 increment by 1;


