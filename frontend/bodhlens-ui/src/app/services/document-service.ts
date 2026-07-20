import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { DocumentResponse } from '../models/document-response';
import { DeleteResponse } from '../models/delete-response';

@Service()
export class DocumentService {

    private http = inject(HttpClient);

    private apiUrl = `http://localhost:8080/api/documents`; 

    getDocuments(): Observable<DocumentResponse[]> {
        return this.http.get<DocumentResponse[]>(this.apiUrl);
    }

    viewDocument(id: string): Observable<Blob> {
        return this.http.get(`${this.apiUrl}/${id}`, {
            responseType: 'blob'
        });
    }

    deleteDocument(id: string): Observable<DeleteResponse> {
        return this.http.delete<DeleteResponse>(`${this.apiUrl}/${id}`);
    }

    getDocumentDetails(id: string): Observable<DocumentResponse> {
        return this.http.get<DocumentResponse>(`${this.apiUrl}/${id}/details`);
    }
}
