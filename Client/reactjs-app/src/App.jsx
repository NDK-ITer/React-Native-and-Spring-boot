import './assets/App.css';
import AppRoute from './routes/AppRoute';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";

function App() {
  return (<>
    <AppRoute />
    <ToastContainer limit={1} />
  </>);
}

export default App;
