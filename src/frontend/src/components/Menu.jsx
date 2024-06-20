import React from "react";
import { Link } from "react-router-dom";
import "./Menu.css";

const Menu = () => (
  <div className="menu">
    <div className="font-bold title">Biblioteca</div>
    <div>
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/books">Cadastro de Livros</Link>
          </li>
        </ul>
      </nav>
    </div>

    <button className="busca">Busca</button>
    <button className="aluguel">Aluguel</button>
  </div>
);

export default Menu;
