<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    HttpSession sessao = request.getSession();
    if (sessao.getAttribute("usuarioLogado") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Empresa</title>
</head>
<body>
    <h1>${empresa.id == null ? 'Adicionar Empresa' : 'Editar Empresa'}</h1>
    <form action="${pageContext.request.contextPath}/empresas/${empresa.id == null ? 'insert' : 'update'}" method="post">
        <input type="hidden" name="id" value="${empresa.id}">
        <label>Nome:</label>
        <input type="text" name="nome" value="${empresa.nome}" required><br>
        <label>Email:</label>
        <input type="email" name="email" value="${empresa.email}" required><br>
        <label>Senha:</label>
        <input type="password" name="senha" value="${empresa.senha}" required><br>
        <label>CNPJ:</label>
        <input type="text" name="cnpj" value="${empresa.cnpj}" required><br>
        <label>Descrição:</label>
        <textarea name="descricao" required>${empresa.descricao}</textarea><br>
        <label>Cidade:</label>
        <input type="text" name="cidade" value="${empresa.cidade}" required><br>
        <input type="submit" value="Salvar">
    </form>
    <a href="${pageContext.request.contextPath}/empresas/list">Voltar</a>
</body>
</html>
