-- Gerado em 06/09/2016
-- projeto test study
-- VERSÃO 28/11/2016
DROP SCHEMA IF EXISTS testStudy;
CREATE SCHEMA IF NOT EXISTS testStudy;
USE testStudy;

DROP TABLE IF EXISTS administrador;
DROP TABLE IF EXISTS adm_morto;
DROP TABLE IF EXISTS examinador;
DROP TABLE IF EXISTS examinador_morto;
DROP TABLE IF EXISTS simulado_questao;
DROP TABLE IF EXISTS realizacao_simulado;
DROP TABLE IF EXISTS simulado;
DROP TABLE IF EXISTS historico;
DROP TABLE IF EXISTS aluno;
DROP TABLE IF EXISTS aluno_morto;
DROP TABLE IF EXISTS turma;
DROP TABLE IF EXISTS prova_questao;
DROP TABLE IF EXISTS alternativa;
DROP TABLE IF EXISTS questao_prova;
DROP TABLE IF EXISTS prova_disciplina;
DROP TABLE IF EXISTS prova_agendada;
DROP TABLE IF EXISTS prova;
DROP TABLE IF EXISTS professor;
DROP TABLE IF EXISTS professor_morto;
DROP TABLE IF EXISTS turma_morto;
DROP TABLE IF EXISTS coord_morto;
DROP TABLE IF EXISTS coordenador;
DROP TABLE IF EXISTS materia;
DROP TABLE IF EXISTS disciplina;
DROP TABLE IF EXISTS log;
DROP TABLE IF EXISTS fazendo_prova;
DROP TABLE IF EXISTS alternativa_simulado;
DROP TABLE IF EXISTS questao_simulado;
DROP TABLE IF EXISTS prova_disciplina;
DROP TABLE IF EXISTS escola_cliente;
DROP TABLE IF EXISTS escola_cliente_morto;

CREATE TABLE IF NOT EXISTS administrador (
id_adm INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
sexo_adm VARCHAR(4) NOT NULL,
email_adm VARCHAR(255) NOT NULL,
foto_adm MEDIUMBLOB ,
nascimento_adm DATE NOT NULL,
cpf_adm VARCHAR(14) NOT NULL,
rg_adm VARCHAR(12) NOT NULL,
nome_adm VARCHAR(100) NOT NULL,
senha_adm VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS escola_cliente (
id_escola_cliente INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
cnpj_emp VARCHAR(18) NOT NULL,
email_emp VARCHAR(255) NOT NULL,
nome_emp VARCHAR(50) NOT NULL,
telefone_emp VARCHAR(14) NOT NULL,
razao_social_emp VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS turma (
id_turma INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_escola INTEGER NOT NULL,
nome_turma VARCHAR(50) NOT NULL,
FOREIGN KEY(id_escola) REFERENCES escola_cliente(id_escola_cliente)
);

CREATE TABLE IF NOT EXISTS aluno (
id_aluno INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_turma INTEGER NOT NULL,
foto_aluno MEDIUMBLOB NOT NULL,
nascimento_aluno DATE NOT NULL,
sexo_aluno VARCHAR(4) NOT NULL,
email_aluno VARCHAR(255) NOT NULL,
rg_aluno VARCHAR(12) NOT NULL,
nome_aluno VARCHAR(100) NOT NULL,
senha_aluno VARCHAR(50) NOT NULL,
FOREIGN KEY(id_turma) REFERENCES turma (id_turma)
);

CREATE TABLE IF NOT EXISTS simulado (
id_simulado INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_aluno INTEGER NOT NULL,
visualizacao_simulado VARCHAR(10) NOT NULL,
FOREIGN KEY(id_aluno) REFERENCES aluno (id_aluno)
);

CREATE TABLE IF NOT EXISTS realizacao_simulado(	
id_realizacao_simulado INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_simulado INTEGER NOT NULL,
hora_inicio TIME NOT NULL,
hora_termino TIME NOT NULL,
data_inicio DATE NOT NULL,
data_termino DATE NOT NULL,
FOREIGN KEY(id_simulado) REFERENCES simulado(id_simulado)
);


CREATE TABLE IF NOT EXISTS professor (
id_professor INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_escola_cliente INTEGER NOT NULL,
sexo_professor VARCHAR(4) NOT NULL,
email_professor VARCHAR(255) NOT NULL,
foto_professor MEDIUMBLOB,
rg_professor VARCHAR(12) NOT NULL,
cpf_professor VARCHAR(14) NOT NULL,
nascimento_professor DATE NOT NULL,
nome_professor VARCHAR(100) NOT NULL,
senha_professor VARCHAR(50) NOT NULL,
FOREIGN KEY(id_escola_cliente) REFERENCES escola_cliente (id_escola_cliente)
);



CREATE TABLE IF NOT EXISTS prova (
id_prova INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_professor INTEGER NOT NULL,
dificuldadeDE VARCHAR(2) NOT NULL,
dificuldadeATE VARCHAR(2) NOT NULL,
n_questoes INTEGER NOT NULL,
nome_prova VARCHAR(50) NOT NULL,
data_prova DATE NOT NULL,
FOREIGN KEY (id_professor) REFERENCES professor (id_professor)
);

CREATE TABLE IF NOT EXISTS prova_agendada(		
id_prova_agendada INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_prova INTEGER NOT NULL,
id_turma INTEGER NOT NULL,
data_termino DATETIME,
data_inicio DATETIME,
FOREIGN KEY (id_prova) REFERENCES prova (id_prova),
FOREIGN KEY (id_turma) REFERENCES turma (id_turma)
);

CREATE TABLE IF NOT EXISTS coordenador (
id_coord INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_escola INTEGER NOT NULL,
email_coord VARCHAR(255) NOT NULL,
nome_coord VARCHAR(100) NOT NULL,
nascimento_coord DATE NOT NULL,
foto_coord MEDIUMBLOB NOT NULL,
cpf_coord VARCHAR(14) NOT NULL,
sexo_coord VARCHAR(4) NOT NULL,
rg_coord VARCHAR(12) NOT NULL,
senha_coord VARCHAR(50) NOT NULL,
FOREIGN KEY (id_escola) REFERENCES escola_cliente(id_escola_cliente)
);

CREATE TABLE IF NOT EXISTS coord_morto (
id_coord_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_coord_antigo INTEGER NOT NULL,
email_coord_morto VARCHAR(255) NOT NULL,
foto_coord_morto MEDIUMBLOB NOT NULL,
nascimento_coord_morto DATE NOT NULL,
nome_coord_morto VARCHAR(100) NOT NULL,
cpf_coord_morto VARCHAR(14) NOT NULL,
rg_coord_morto VARCHAR(12) NOT NULL,
sexo_coord_morto VARCHAR(4) NOT NULL,
senha_coord_morto VARCHAR(50) NOT NULL	
);

CREATE TABLE IF NOT EXISTS adm_morto (
id_adm_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_adm_antigo INTEGER NOT NULL,
foto_adm_antigo MEDIUMBLOB NOT NULL,
cpf_adm_amtigo VARCHAR(14) NOT NULL,
nome_adm_antigo VARCHAR(100) NOT NULL,
sexo_adm_antigo VARCHAR(4) NOT NULL,
email_adm_antigo VARCHAR(255) NOT NULL,
rg_adm_antigo VARCHAR(12) NOT NULL,
nascimento_adm_antigo DATE NOT NULL,
senha_adm_morto VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS disciplina (
id_disciplina INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
nome_disciplina VARCHAR(50) NOT NULL,
padrao_disciplina VARCHAR(7) NOT NULL,
escola_disciplina INTEGER,
FOREIGN KEY(escola_disciplina) REFERENCES escola_cliente (id_escola_cliente)
);

SELECT * FROM disciplina;

CREATE TABLE IF NOT EXISTS examinador (
id_examinador INTEGER PRIMARY KEY AUTO_INCREMENT,
email_examinador VARCHAR(255) NOT NULL,
foto_examinador MEDIUMBLOB NOT NULL,
rg_examinador VARCHAR(12) NOT NULL,
sexo_examinador VARCHAR(4) NOT NULL,
nome_examinador VARCHAR(100) NOT NULL,
cpf_examinador VARCHAR(14) NOT NULL,
nascimento_examinador DATE NOT NULL,
senha_examinador VARCHAR(50) NOT NULL,
disciplina_examinador INTEGER NOT NULL,
FOREIGN KEY(disciplina_examinador) REFERENCES disciplina(id_disciplina)
);

CREATE TABLE IF NOT EXISTS materia (
id_materia INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
id_disciplina INTEGER NOT NULL,
nome_materia VARCHAR(50) NOT NULL,
FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina)
);

CREATE TABLE IF NOT EXISTS aluno_morto (
id_aluno_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
email_aluno_morto VARCHAR(255) NOT NULL,
id_aluno_antigo INTEGER NOT NULL,
sexo_aluno_morto VARCHAR(4) NOT NULL,
id_escola_morto VARCHAR(10) NOT NULL,
foto_aluno_morto MEDIUMBLOB NOT NULL,
rg_aluno_morto VARCHAR(12) NOT NULL,
nome_aluno_morto VARCHAR(50) NOT NULL,
cpf_aluno_morto VARCHAR(14) NOT NULL,
nascimento_aluno_morto VARCHAR(4) NOT NULL,
senha_aluno_morto VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS escola_cliente_morto (
id_escola_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
cnpj_emp_morto VARCHAR(18) NOT NULL,
id_escola_antigo INTEGER NOT NULL,
razao_social_morto VARCHAR(100) NOT NULL,
telefone_emp_morto VARCHAR(14) NOT NULL,
email_emp_morto VARCHAR(255) NOT NULL,
nome_emp_morto VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS turma_morto (
id_turma_morto INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_turma_antigo INTEGER NOT NULL,
id_aluno_antigo INTEGER NOT NULL,
id_escola_morto INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS examinador_morto (
id_exam_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
email_exam_morto VARCHAR(255) NOT NULL,
nascimento_exam_morto DATE NOT NULL,
id_exam_antigo INTEGER NOT NULL,
sexo_exam_morto VARCHAR(4) NOT NULL,
rg_exam_morto VARCHAR(12) NOT NULL,
foto_exam_morto MEDIUMBLOB NOT NULL,
cpf_exam_morto VARCHAR(14) NOT NULL,
nome_exam_morto VARCHAR(100) NOT NULL,
senha_exam_morto VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS professor_morto (
id_professor_morto INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
nome_professor_morto VARCHAR(100) NOT NULL,
id_professor_antigo INTEGER NOT NULL,
sexo_professor_antigo VARCHAR(4) NOT NULL,
nascimento_professor_morto DATE NOT NULL,
email_professor_morto VARCHAR(255) NOT NULL,
foto_professor_morto MEDIUMBLOB NOT NULL,
rg_professor_morto VARCHAR(12) NOT NULL,
cpf_professor_morto VARCHAR(14) NOT NULL,
senha_professor_morto VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS log (
id_log INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
nome_usuario VARCHAR(100) NOT NULL,
tipo_usuario VARCHAR(15) NOT NULL,
email_usuario VARCHAR(255) NOT NULL,
acao_usuario TEXT NOT NULL,
data_acao DATE NOT NULL,
horario_acao TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS questao_prova (
id_questao INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
uso_questao INTEGER DEFAULT 0,
ultimo_uso_questao DATE DEFAULT '2016-10-10',
visualizacao_questao VARCHAR(10) NOT NULL,
corpo_questao TEXT NOT NULL,
titulo_questao VARCHAR(50) NOT NULL,
tipo_questao VARCHAR(10) NOT NULL,
disciplina_questao INTEGER NOT NULL,
dificuldade INTEGER,
materia_questao INTEGER NOT NULL,
disponibilidade_questao VARCHAR(4), -- SE O CRIADOR DA QUESTÃO DESEJA TORNÁ-LA PÚBLICA
status_questao VARCHAR(15), -- STATUS DA QUESTÃO EM RELAÇÃO AO EXAMINADOR (PENDENTE, ACEITA, RECUSADA, ETC..) 
examinador_responsavel_questao INTEGER DEFAULT 0,
autor_questao INTEGER,
FOREIGN KEY (autor_questao) REFERENCES professor(id_professor),
FOREIGN KEY (disciplina_questao) REFERENCES disciplina(id_disciplina),
FOREIGN KEY (materia_questao) REFERENCES materia(id_materia),
FOREIGN KEY (examinador_responsavel_questao) REFERENCES examinador(id_examinador)
);

CREATE TABLE IF NOT EXISTS alternativa (
id_alternativa INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_questao INTEGER NOT NULL,
corpo_alternativa TEXT NOT NULL,
certa_prova VARCHAR(1) NOT NULL,
FOREIGN KEY(id_questao) REFERENCES questao_prova (id_questao)
);

CREATE TABLE IF NOT EXISTS fazendo_prova(
	id_fazendo_prova INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_aluno INTEGER NOT NULL,
    id_questao_prova INTEGER NOT NULL,
    resposta TEXT,
    id_alternativa INTEGER,
    id_prova_agendada INTEGER,
    FOREIGN KEY (id_prova_agendada) REFERENCES prova_agendada(id_prova_agendada),
    FOREIGN KEY(id_aluno)REFERENCES aluno(id_aluno),
    FOREIGN KEY(id_questao_prova) REFERENCES questao_prova(id_questao),
    FOREIGN KEY(id_alternativa) REFERENCES alternativa(id_alternativa)
);

CREATE TABLE IF NOT EXISTS questao_simulado (
id_questao_simulado INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
corpo_questao_simulado TEXT NOT NULL,
titulo_questao_simulado VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS alternativa_simulado (
id_alternativa INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_questao_simulado INTEGER NOT NULL,
corpo_alternativa TEXT NOT NULL,
certa_simulado VARCHAR(1) NOT NULL,
FOREIGN KEY(id_questao_simulado) REFERENCES questao_simulado (id_questao_simulado)
);

CREATE TABLE IF NOT EXISTS historico (
id_historico INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_aluno INTEGER NOT NULL,
id_prova INTEGER NOT NULL,
nota_prova DOUBLE NOT NULL,
data_prova_historico DATE NOT NULL,
nota_simulado DOUBLE NOT NULL,
data_simulado_historico DATE NOT NULL,
FOREIGN KEY(id_aluno) REFERENCES aluno (id_aluno),
FOREIGN KEY(id_prova) REFERENCES prova (id_prova)
);


CREATE TABLE IF NOT EXISTS prova_disciplina (
id_prova_disciplina INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_disciplina INTEGER NOT NULL,
id_prova INTEGER NOT NULL,
FOREIGN KEY(id_disciplina) REFERENCES disciplina (id_disciplina),
FOREIGN KEY(id_prova) REFERENCES prova (id_prova)
);

CREATE TABLE IF NOT EXISTS prova_questao (
id_prova_questao INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_questao INTEGER NOT NULL,
id_prova INTEGER NOT NULL,
FOREIGN KEY(id_questao) REFERENCES questao_prova (id_questao),
FOREIGN KEY(id_prova) REFERENCES prova (id_prova)
);

CREATE TABLE IF NOT EXISTS simulado_questao (
id_simulado_questao INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
id_questao_simulado INTEGER NOT NULL,
id_simulado INTEGER NOT NULL,
FOREIGN KEY(id_questao_simulado) REFERENCES questao_simulado (id_questao_simulado),
FOREIGN KEY(id_simulado) REFERENCES simulado (id_simulado)
);

CREATE TABLE IF NOT EXISTS mensagem(
id_mensagem INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
titulo_mensagem VARCHAR(100) NOT NULL,
remetente INTEGER NOT NULL,
corpo_mensagem TEXT NOT NULL,
destinatario INTEGER NOT NULL,
questao_mensagem INTEGER NOT NULL,
FOREIGN KEY(remetente) REFERENCES examinador(id_examinador),
FOREIGN KEY(destinatario) REFERENCES professor(id_professor),
FOREIGN KEY(questao_mensagem) REFERENCES questao_prova(id_questao)
);



ALTER TABLE prova ADD FOREIGN KEY(id_professor) REFERENCES professor (id_professor);
ALTER TABLE materia ADD FOREIGN KEY(id_disciplina) REFERENCES disciplina (id_disciplina);
ALTER TABLE `teststudy`.`prova_agendada` CHANGE COLUMN `data_termino` `data_termino` DATETIME NOT NULL ,
CHANGE COLUMN `data_inicio` `data_inicio` DATETIME NOT NULL ,
ADD COLUMN `duracao_prova` INT(3) NOT NULL AFTER `data_inicio`;

ALTER TABLE `teststudy`.`log` 
DROP COLUMN `horario_acao`,
CHANGE COLUMN `data_acao` `data_acao` DATETIME NOT NULL ;

ALTER TABLE `teststudy`.`aluno_morto` 
DROP COLUMN `cpf_aluno_morto`,
CHANGE COLUMN `id_escola_morto` `id_turma_morto` VARCHAR(10) NOT NULL ;

ALTER TABLE `teststudy`.`aluno_morto` 
CHANGE COLUMN `nascimento_aluno_morto` `nascimento_aluno_morto` DATE NOT NULL ;

ALTER TABLE `teststudy`.`turma_morto` 
DROP COLUMN `id_aluno_antigo`;

ALTER TABLE `teststudy`.`turma_morto` 
ADD COLUMN `nome_turma_morto` VARCHAR(50) NOT NULL AFTER `id_escola_morto`;

ALTER TABLE `teststudy`.`log` 
CHANGE COLUMN `tipo_usuario` `tipo_usuario` VARCHAR(150) NOT NULL ;


INSERT INTO administrador(sexo_adm,
 email_adm,
 nascimento_adm,
cpf_adm,
 rg_adm,
 nome_adm,
 senha_adm) VALUES (
 'masc',
 'falecom.josefelipe@hotmail.com',
 '1999-06-09',
 '453.367.578-61',
 '50.773.139-6',
 'José Felipe da Silva',
 '123'
 );

