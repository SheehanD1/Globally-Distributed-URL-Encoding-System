import { createContext, useContext, useState, useEffect } from 'react';
import api from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (token) {
            setIsLoading(false);
        } else {
            setIsLoading(false);
        }
    }, [token]);

    const login = async (username, password) => {
        const response = await api.post('/auth/public/login', { username, password });
        const { token: newToken } = response.data;
        localStorage.setItem('token', newToken);
        setToken(newToken);
        setUser({ username });
        return response.data;
    };

    const register = async (username, email, password) => {
        const response = await api.post('/auth/public/register', { username, email, password });
        return response.data;
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
        setUser(null);
    };

    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider value={{ user, token, isAuthenticated, isLoading, login, register, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};
