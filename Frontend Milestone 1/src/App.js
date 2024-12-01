// App.js

// App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from '../src/Components/Login/Login';
import Register from '../src/Components/Login/Register';
import LandingPage from '../src/Components/PortMgmt/LandingPage';
import ForgotPassword from './Components/Login/ForgotPassword';
import OTPVerification from './Components/Login/OTPVerification';
import ResetPassword from './Components/Login/ResetPassword';
import Dashboard from './Components/PortMgmt/Dashboard';
import Market from './Components/PortMgmt/Market';
// import Portfolio from './Components/PortMgmt/Portfolio';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/landingpg" element={<LandingPage />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/otp-verification" element={<OTPVerification />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/dashboard" element={< Dashboard />} />
        <Route path="/market" element={< Market />} />
        {/* <Route path="/portfolio" element={< Portfolio />} /> */}


      </Routes>
    </Router>
  );
};

export default App;


// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import Login from '../src/Components/Login/Login';
// import Register from '../src/Components/Login/Register';
// import LandingPage from '../src/Components/PortMgmt/LandingPage';
// import ForgotPassword from './Components/Login/ForgotPassword';
// import OTPVerification from './Components/Login/OTPVerification';
// import ResetPassword from './Components/Login/ResetPassword';
// import Dashboard from './Components/PortMgmt/Dashboard';
// import Market from './Components/PortMgmt/Market';
// // import Portfolio from './Components/PortMgmt/Portfolio';

// const App = () => {
//   return (
//     <Router>
//       <Routes>
//         <Route path="/" element={<Login />} />
//         <Route path="/register" element={<Register />} />
//         <Route path="/landingpg" element={<LandingPage />} />
//         <Route path="/forgot-password" element={<ForgotPassword />} />
//         <Route path="/otp-verification" element={<OTPVerification />} />
//         <Route path="/reset-password" element={<ResetPassword />} />
//         <Route path="/dashboard" element={< Dashboard />} />
//         <Route path="/market" element={< Market />} />
//         {/* <Route path="/portfolio" element={< Portfolio />} /> */}
        
        
//       </Routes>
//     </Router>
//   );
// };

//export default App;
