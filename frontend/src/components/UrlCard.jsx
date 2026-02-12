import './UrlCard.css';

const UrlCard = ({ url }) => {
    const shortLink = `${window.location.origin}/${url.shortUrl}`;
    const formattedDate = new Date(url.createdDate).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    });

    const handleCopy = () => {
        navigator.clipboard.writeText(shortLink);
    };

    return (
        <div className="url-card">
            <div className="url-card-main">
                <a href={shortLink} className="url-short" target="_blank" rel="noopener noreferrer">
                    {shortLink}
                </a>
                <p className="url-original" title={url.originalUrl}>
                    {url.originalUrl}
                </p>
            </div>
            <div className="url-card-meta">
                <span className="url-clicks">🖱 {url.clickCount} clicks</span>
                <span className="url-date">📅 {formattedDate}</span>
                <button onClick={handleCopy} className="url-copy-btn">📋 Copy</button>
            </div>
        </div>
    );
};

export default UrlCard;
