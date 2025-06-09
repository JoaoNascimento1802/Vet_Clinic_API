import axiosInstance from './axiosInstance';

/**
 * Gera e baixa o relatório de consultas em PDF.
 * @param {object} filters - Objeto com os filtros, ex: { veterinarioId: 1, speciality: 'CLINICO_GERAL' }
 */
export const generateConsultationsReport = async (filters) => {
  try {
    // Constrói os parâmetros da URL a partir do objeto de filtros
    const params = new URLSearchParams();
    if (filters.veterinarioId) {
      params.append('veterinarioId', filters.veterinarioId);
    }
    if (filters.speciality) {
      params.append('speciality', filters.speciality);
    }

    const response = await axiosInstance.get('/reports/consultations-pdf', {
      params: params,
      responseType: 'blob', // ESSENCIAL: Diz ao Axios para tratar a resposta como um arquivo
    });

    // Lógica para forçar o download do arquivo no navegador
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'relatorio_consultas.pdf'); // Nome do arquivo
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link); // Limpa o link da DOM

  } catch (error) {
    console.error("Error generating report:", error);
    // Tenta mostrar uma mensagem de erro mais amigável
    alert("Não foi possível gerar o relatório. Verifique os filtros e tente novamente.");
    throw error;
  }
};