import React, { useState, useEffect, useCallback } from 'react';
import { getAllUsers, deleteUser } from '../../api/userService';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import useAuth from '../../hooks/useAuth';

const tableCellStyle = { padding: '8px', border: '1px solid #ddd' };
const buttonStyle = { marginRight: '5px', padding: '5px 10px', border: '1px solid', borderRadius: '4px', cursor: 'pointer' };

const AdminUsersPage = () => {
    const { user: adminUser } = useAuth(); // Pega o admin logado para evitar auto-exclusão
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const fetchUsers = useCallback(async () => {
        try {
            setLoading(true);
            const data = await getAllUsers();
            setUsers(data);
        } catch (err) {
            setError('Falha ao carregar usuários.');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchUsers();
    }, [fetchUsers]);

    const handleDelete = async (userIdToDelete) => {
        if (userIdToDelete === adminUser.id) {
            alert("Você não pode excluir sua própria conta de administrador.");
            return;
        }

        if (window.confirm('AVISO: Excluir um usuário também excluirá todos os seus pets e consultas associadas. Deseja continuar?')) {
            try {
                await deleteUser(userIdToDelete);
                fetchUsers(); // Recarrega a lista
            } catch (err) {
                alert('Falha ao excluir usuário.');
            }
        }
    };

    if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

    return (
        <MainLayout>
            <h1>Gerenciar Usuários</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                <thead>
                    <tr style={{ backgroundColor: '#f2f2f2' }}>
                        <th style={tableCellStyle}>ID</th>
                        <th style={tableCellStyle}>Username</th>
                        <th style={tableCellStyle}>Email</th>
                        <th style={tableCellStyle}>Telefone</th>
                        <th style={tableCellStyle}>Role</th>
                        <th style={tableCellStyle}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td style={tableCellStyle}>{user.id}</td>
                            <td style={tableCellStyle}>{user.username}</td>
                            <td style={tableCellStyle}>{user.email}</td>
                            <td style={tableCellStyle}>{user.phone}</td>
                            <td style={tableCellStyle}>{user.role}</td>
                            <td style={tableCellStyle}>
                                <button 
                                    onClick={() => handleDelete(user.id)} 
                                    style={{ ...buttonStyle, backgroundColor: '#dc3545', color: 'white' }}
                                    disabled={user.id === adminUser.id} // Desabilita o botão para o próprio admin
                                >
                                    Excluir
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </MainLayout>
    );
};

export default AdminUsersPage;