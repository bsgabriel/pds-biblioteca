import React from "react";
import "./BookList.css";

const BookList = ({ books, onEdit, onDelete }) => (
  <div className="book-list">
    <table>
      <thead>
        <tr className="text-right p-4">
          <th>Titulo</th>
          <th>Autor</th>
          <th>Editora</th>
          <th>Ano</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {books.map((book) => (
          <tr key={book.id}>
            <td>{book.title}</td>
            <td>{book.author}</td>
            <td>{book.publisher}</td>
            <td>{book.year}</td>
            <td>
              <button onClick={() => onEdit(book)} className="edit mr-2">
                Editar
              </button>
              <button onClick={() => onDelete(book.id)}>Excluir</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

export default BookList;
