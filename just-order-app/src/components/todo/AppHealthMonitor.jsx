import { HealthApi } from './api/ProductApiService';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { APPS } from './constants';

const AppHealthMonitor = () => {
  const [appHealthStatus, setAppHealthStatus] = useState([]);

  useEffect(() => {
    const fetchAppHealthStatus = async () => {
      const promises = APPS.map(async (app) => {
        const url = app.link + HealthApi;
        try {
          const response = await axios.get(url);
          return { appName: app.name, status: response.data.status };
        } catch (error) {
          return { appName: app.name, status: 'DOWN' };
        }
      });

      const healthStatuses = await Promise.all(promises);
      setAppHealthStatus(healthStatuses);
    };

    // Fetch initially
    fetchAppHealthStatus();

    // Fetch every 2 seconds
    const interval = setInterval(fetchAppHealthStatus, 5000);

    // Clean up function to clear interval when component unmounts or re-renders
    return () => clearInterval(interval);
  }, []); // Empty dependency array ensures the effect runs only once after initial render

  return (

    <div className="sticky-bottom py-3">
      <div className="container d-flex justify-content-center align-items-center">
      <div className="card col-md-10 bg-light border-light shadow-sm" >

        <div className="card-body">
          <button className='btn btn-dark mx-2'> Service Status</button>
        {appHealthStatus.map((app, index) => (
          <button
            key={index}
            type="button"
            className={`btn btn-outline-${app.status === 'UP' ? 'success' : 'danger'}`}
            style={{ marginRight: '7px' }}
          >
            {app.appName}
          </button>
        ))}
        </div>
      </div>
        

      </div>
    </div>
  );
};

export default AppHealthMonitor;
