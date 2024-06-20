import React, { useState } from "react";
import { Link } from "react-router-dom";
import BookList from "../components/BookList";
import Layout from "../components/Layout";
import "../styles/Home.css";

const Home = () => {
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

  const handleEditBook = (book) => {
    window.location.href = `/books?edit=${book.id}`;
  };

  const handleDeleteBook = (id) => {
    setBooks(books.filter((book) => book.id !== id));
  };

  return (
    <Layout>
      <h1 className="flex ml-20 text-4xl font-bold ">Livros</h1>
      <BookList
        books={books}
        onEdit={handleEditBook}
        onDelete={handleDeleteBook}
      />
      <div className="flex justify-end items-center">
        <Link to="/books" className="add-book-button">
          <button
            className="flex-auto bg-blue-500 hover:bg-blue-700
                 text-white font-bold py-2 px-4 rounded
                    "
          >
            Novo Livro
          </button>
        </Link>
      </div>
    </Layout>
  );
};

export default Home;
