import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import BookForm from "../components/BookForm";
import Layout from "../components/Layout";

const BooksPage = () => {
  const [books, setBooks] = useState([
    {
      id: 1,
      title: "Mock Book 1",
      author: "Author 1",
      publisher: "Publisher 1",
      year: 2021,
    },
    {
      id: 2,
      title: "Mock Book 2",
      author: "Author 2",
      publisher: "Publisher 2",
      year: 2022,
    },
  ]);

  const [editingBook, setEditingBook] = useState(null);
  const [searchParams] = useSearchParams();
  const editId = searchParams.get("edit");

  useEffect(() => {
    if (editId) {
      const bookToEdit = books.find((book) => book.id === parseInt(editId));
      setEditingBook(bookToEdit);
    } else {
      setEditingBook(null);
    }
  }, [editId, books]);

  const handleSaveBook = (newBook) => {
    if (editingBook) {
      setBooks(
        books.map((book) => (book.id === editingBook.id ? newBook : book))
      );
    } else {
      newBook.id = books.length + 1;
      setBooks([...books, newBook]);
    }
    window.location.href = "/";
  };

  return (
    <Layout>
      <h1 className="flex ml-20 text-4xl font-bold ">
        {editingBook ? "Editar livro" : "Adicionar livro"}
      </h1>
      <BookForm book={editingBook} onSave={handleSaveBook} />
    </Layout>
  );
};

export default BooksPage;
