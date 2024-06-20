import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import BooksPage from "./pages/BooksPage";
import Home from "./pages/Home";
import "./styles/App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/books" element={<BooksPage />} />
      </Routes>
    </Router>
  );
}

export default App;
