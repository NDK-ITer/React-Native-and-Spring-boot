import React from 'react';
import ReactDOM from 'react-dom/client';
import './assets/index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router} from 'react-router-dom';
import { UserProvider } from './contexts/UserContext';
import { RoleProvider } from './contexts/RoleContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Router>
      <RoleProvider>
      <UserProvider>
        <App />
      </UserProvider>
      </RoleProvider>
    </Router>
  </React.StrictMode>
);
reportWebVitals();
