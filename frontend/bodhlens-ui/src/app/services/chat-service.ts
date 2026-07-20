import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { ChatRequest } from '../models/chat-request';
import { Observable } from 'rxjs';
import { ChatResponse } from '../models/chat-response';

@Service()
export class ChatService {

    private httpClient = inject(HttpClient);

    private apiUrl = `http://localhost:8080/api/chat`; 

    chat(documentId: string, req: ChatRequest): Observable<ChatResponse> {
        return this.httpClient.post<ChatResponse>(
            `${this.apiUrl}/${documentId}`,
            req
        );
    }
}
