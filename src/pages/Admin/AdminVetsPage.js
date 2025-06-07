import React, { useState, useEffect, useCallback } from 'react';
import { getAllVeterinarians, deleteVeterinary } from '../../api/vetService';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';

const tableCellStyle = { padding: '8px', border: '1px solid #ddd' };
const buttonStyle = { marginRight: '5px', padding: '5px 10px', border: '1px solid', borderRadius: '4px', cursor: 'pointer' };

const AdminVetsPage = () => {
    const [vets, setVets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const fetchVets = useCallback(async () => {
        try {
            setLoading(true);
            const data = await getAllVeterinarians();
            setVets(data);
        } catch (err) {
            setError('Falha ao carregar veterinários.');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchVets();
    }, [fetchVets]);

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir este veterinário? Esta ação não pode ser desfeita.')) {
            try {
                await deleteVeterinary(id);
                fetchVets(); // Recarrega a lista após a exclusão
            } catch (err) {
                alert('Falha ao excluir veterinário.');
            }
        }
    };

    // Lógica para adicionar/editar (pode ser expandida com um modal)
    const handleAdd = () => {
        alert("Funcionalidade de 'Adicionar' a ser implementada com um formulário/modal.");
        // Aqui você abriria um modal e chamaria a função addVeterinary do serviço.
    };

    const handleEdit = (vet) => {
        alert(`Funcionalidade de 'Editar' para ${vet.name} a ser implementada.`);
        // Aqui você abriria um modal pré-preenchido com os dados do 'vet'
        // e chamaria a função updateVeterinary do serviço.
    };

    if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

    return (
        <MainLayout>
            <h1>Gerenciar Veterinários</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <button onClick={handleAdd} style={{ ...buttonStyle, backgroundColor: '#28a745', color: 'white', marginBottom: '20px' }}>
                Adicionar Novo Veterinário
            </button>
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                <thead>
                    <tr style={{ backgroundColor: '#f2f2f2' }}>
                        <th style={tableCellStyle}>Nome</th>
                        <th style={tableCellStyle}>Email</th>
                        <th style="...tableCellStyle">CRMV</th>
                        <th style="...tableCellStyle">Especialidade</th>
                        <th style="...tableCellStyle">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {vets.map(vet => (
                        <tr key={vet.id}>
                            <td style={tableCellStyle}>{vet.name}</td>
                            <td style={tableCellStyle}>{vet.email}</td>
                            <td style={tableCellStyle}>{vet.crmv}</td>
                            <td style={tableCellStyle}>{vet.specialityenum}</td>
                            <td style={tableCellStyle}>
                                <button onClick={() => handleEdit(vet)} style={{ ...buttonStyle, backgroundColor: '#ffc107' }}>
                                    Editar
                                </button>
                                <button onClick={() => handleDelete(vet.id)} style={{ ...buttonStyle, backgroundColor: '#dc3545', color: 'white' }}>
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

export default AdminVetsPage;