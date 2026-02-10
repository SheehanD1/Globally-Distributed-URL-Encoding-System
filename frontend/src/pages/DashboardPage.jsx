import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import ShortenForm from '../components/ShortenForm';
import UrlCard from '../components/UrlCard';
import api from '../services/api';
import './DashboardPage.css';

const DashboardPage = () => {
    const [urls, setUrls] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');
    const { logout, user } = useAuth();
    const navigate = useNavigate();

    const fetchUrls = async () => {
        try {
            const response = await api.get('/urls/myurls');
            setUrls(response.data);
        } catch (err) {
            setError('Failed to load URLs');
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchUrls();
    }, []);

    const handleUrlShortened = (newUrl) => {
        setUrls([newUrl, ...urls]);
    };

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <h1>🔗 URL Shortener</h1>
                <div className="header-actions">
                    <span className="welcome-text">Welcome back!</span>
                    <button onClick={handleLogout} className="logout-btn">Logout</button>
                </div>
            </header>

            <main className="dashboard-main">
                <ShortenForm onUrlShortened={handleUrlShortened} />

                <section className="urls-section">
                    <h2>Your URLs</h2>
                    {isLoading && <p className="loading-text">Loading your URLs...</p>}
                    {error && <p className="error-text">{error}</p>}
                    {!isLoading && urls.length === 0 && (
                        <p className="empty-text">No URLs yet. Shorten your first URL above!</p>
                    )}
                    <div className="urls-list">
                        {urls.map((url) => (
                            <UrlCard key={url.id} url={url} />
                        ))}
                    </div>
                </section>
            </main>
        </div>
    );
};

export default DashboardPage;
