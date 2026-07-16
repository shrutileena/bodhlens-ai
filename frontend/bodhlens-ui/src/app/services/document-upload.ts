import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

@Service()
export class DocumentUpload {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/api/documents/upload';

    upload(file: File): Observable<any> {

        const formData = new FormData();
        
        formData.append('file', file);

        return this.http.post(this.apiUrl, 
            formData
        );
    }
}
