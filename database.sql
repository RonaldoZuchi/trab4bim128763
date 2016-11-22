CREATE DATABASE database;

CREATE TABLE tb_usuario(
    id_usuario INTEGER AUTO_INCREMENT PRIMARY KEY,
    ds_login VARCHAR(30) NOT NULL,
    ds_senha VARCHAR(30) NOT NULL
);

CREATE TABLE tb_pessoa(
    id_pessoa INT AUTO_INCREMENT PRIMARY KEY,
    nm_pessoa VARCHAR(70) NOT NULL,
    fl_sexo CHAR(1) NOT NULL,
    dt_cadastro DATETIME NOT NULL,
    ds_email VARCHAR(80) NOT NULL,
    ds_endereco VARCHAR(200) NOT NULL,
    fl_origemCadastro CHAR(1) NOT NULL,
    id_usuario_cadastro INT NOT NULL,
    FOREIGN KEY(id_usuario_cadastro) REFERENCES tb_usuario(id_usuario)
);

INSERT INTO tb_usuario (ds_login,ds_senha) VALUES('admin','123456');