package com.example.kafka.architecture.icompras.clientes.model;


import jakarta.persistence.*;
import lombok.Data;

/*
	codigo serial not null primary key,
	nome varchar(150) not null,
	cpf char(11) not null,
	logradouro varchar(100),
	numero varchar(10),
	bairro varchar(100),
	email varchar(150),
	telefone varchar(20)
* */

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name ="cpf", length = 11)
    private String cpf;

    @Column(name = "logradouro", length = 100)
    private String logradouro;

    @Column(name = "numero", length = 100)
    private String numero;

    @Column(name="bairro", length = 100)
    private String bairro;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

}
