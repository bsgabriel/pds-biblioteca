import React, { useEffect, useState } from "react";
import "./BookForm.css";

const BookForm = ({ book, onSave }) => {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [publisher, setPublisher] = useState("");
  const [year, setYear] = useState("");

  useEffect(() => {
    if (book) {
      setTitle(book.title);
      setAuthor(book.author);
      setPublisher(book.publisher);
      setYear(book.year);
    } else {
      setTitle("");
      setAuthor("");
      setPublisher("");
      setYear("");
    }
  }, [book]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const newBook = {
      id: book ? book.id : null,
      title,
      author,
      publisher,
      year,
    };
    onSave(newBook);
  };

  return (
    <form onSubmit={handleSubmit} className="book-form">
      <div>
        <label>Titulo</label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Autor</label>
        <input
          type="text"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Editora</label>
        <input
          type="text"
          value={publisher}
          onChange={(e) => setPublisher(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Ano</label>
        <input
          type="number"
          value={year}
          onChange={(e) => setYear(e.target.value)}
          required
        />
      </div>
      <button type="submit">Salvar</button>
    </form>
  );
};

export default BookForm;
