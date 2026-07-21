import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { ChatSession } from '../models/chat-session';

@Service()
export class ChatSessionService {

    private httpClient = inject(HttpClient);

    private readonly apiUrl = 'http://localhost:8080/api/chat-sessions';

    getSessions(documentId: string): Observable<ChatSession[]> {
        return this.httpClient.get<ChatSession[]>(
            `${this.apiUrl}/documents/${documentId}`
        );
    }

    createSession(documentId: string): Observable<ChatSession> {
        return this.httpClient.post<ChatSession>(
            `${this.apiUrl}/documents/${documentId}`,
            {}  
        );
    }

    deleteSession(sessionId: string) {
        return this.httpClient.delete(`${this.apiUrl}/${sessionId}`);
    }
}
