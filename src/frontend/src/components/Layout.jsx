import React from "react";
import "./Layout.css";
import Menu from "./Menu";

const Layout = ({ children }) => (
  <div className="layout">
    <Menu />
    <main className="main">{children}</main>
  </div>
);

export default Layout;
