import React from "react";
import { Link } from "react-router-dom";
import logo from "../logo.svg";
import styled, { keyframes } from "styled-components";

const AppLogoSpin = (from, to) => keyframes`
  from {
    transform: rotate(${from}deg);
  }
  to {
    transform: rotate(${to}deg);
  }
`;

const StyledNotFound = styled.div`
  text-align: center;
  .logo {
    @media (prefers-reduced-motion: no-preference) {
      animation: ${AppLogoSpin(0, 360)} infinite 20s linear;
    }
    height: 40vmin;
    pointer-events: none;
  }
  .header {
    background-color: #282c34;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: calc(10px + 2vmin);
    color: white;
  }
  .link {
    color: #61dafb;
  }
`;

function NotFound() {
  return (
    <StyledNotFound>
      <header className="header">
        <img src={logo} className="logo" alt="logo" />
        Path: {window.location.href} not found!
        <span className="link">
          <Link to="/">Go Home Brother</Link>
        </span>
      </header>
    </StyledNotFound>
  );
}

export default NotFound;
