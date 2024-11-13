-- Tabela para armazenar os usuários
CREATE TABLE Usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT 0 -- 0 = usuário comum, 1 = administrador
);

-- Tabela para armazenar as perguntas feitas pelos usuários
CREATE TABLE Perguntas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_usuario INTEGER NOT NULL, -- Referência ao usuário que fez a pergunta
    pergunta TEXT NOT NULL,
    resposta TEXT, -- Pode ser NULL inicialmente até ser respondida
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_resposta DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
);
