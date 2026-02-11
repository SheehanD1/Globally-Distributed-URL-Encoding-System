import { useState } from 'react';
import api from '../services/api';
import './ShortenForm.css';

const ShortenForm = ({ onUrlShortened }) => {
    const [originalUrl, setOriginalUrl] = useState('');
    const [shortenedUrl, setShortenedUrl] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [copied, setCopied] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setShortenedUrl('');
        setCopied(false);
        setIsLoading(true);

        try {
            const response = await api.post('/urls/shorten', { originalUrl });
            const shortCode = response.data.shortUrl;
            const fullShortUrl = `${window.location.origin}/${shortCode}`;
            setShortenedUrl(fullShortUrl);
            onUrlShortened(response.data);
            setOriginalUrl('');
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to shorten URL');
        } finally {
            setIsLoading(false);
        }
    };

    const handleCopy = () => {
        navigator.clipboard.writeText(shortenedUrl);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
    };

    return (
        <div className="shorten-form-container">
            <h2>Shorten a URL</h2>
            <form onSubmit={handleSubmit} className="shorten-form">
                <input
                    type="url"
                    value={originalUrl}
                    onChange={(e) => setOriginalUrl(e.target.value)}
                    placeholder="Paste your long URL here..."
                    required
                />
                <button type="submit" disabled={isLoading}>
                    {isLoading ? 'Shortening...' : 'Shorten'}
                </button>
            </form>

            {error && <p className="shorten-error">{error}</p>}

            {shortenedUrl && (
                <div className="shorten-result">
                    <span className="short-url">{shortenedUrl}</span>
                    <button onClick={handleCopy} className="copy-btn">
                        {copied ? '✓ Copied!' : '📋 Copy'}
                    </button>
                </div>
            )}
        </div>
    );
};

export default ShortenForm;
