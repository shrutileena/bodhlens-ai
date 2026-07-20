import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackButton } from '../../shared/back-button/back-button';
import { DocumentResponse } from '../../models/document-response';
import { DocumentService } from '../../services/document-service';
import { ChatMessage } from '../../models/chat-message';
import { DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ChatService } from '../../services/chat-service';

@Component({
  selector: 'app-chat',
  imports: [BackButton, DatePipe, ReactiveFormsModule],
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat implements OnInit {
  private route = inject(ActivatedRoute);

  private router = inject(Router);

  documentId?: string;

  document!: DocumentResponse;

  documents: DocumentResponse[] = [];

  messages: ChatMessage[] = [];

  private documentService = inject(DocumentService);

  private cdr = inject(ChangeDetectorRef);

  private fb = inject(FormBuilder);

  private chatService = inject(ChatService);

  isThinking = false;

  chatForm = this.fb.group({
    question: ['', Validators.required],
  });

  ngOnInit(): void {
    this.documentId = this.route.snapshot.paramMap.get('documentId') ?? undefined;

    if (this.documentId) {
      this.getDocumentDetails();
    } else {
      this.loadDocuments();
      this.addAskAiWelcomeMessage();
    }
  }

  getDocumentDetails(): void {
    if(!this.documentId){
      return;
    }
    this.documentService.getDocumentDetails(this.documentId).subscribe({
      next: (response) => {
        this.document = response;
        this.addWelcomeMessage();
        this.cdr.detectChanges();
      },
    });
  }

  loadDocuments(): void {
    this.documentService.getDocuments().subscribe({
      next: (response) => {
        this.documents = response;

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  private addWelcomeMessage(): void {
    this.messages.push({
      sender: 'AI',
      message: `Hello! I've loaded "${this.document.fileName}". Ask me anything about this document`,
      timestamp: new Date(),
    });
  }

  send(): void {
    if(!this.documentId){
      return;
    }
    if (this.chatForm.invalid) {
      return;
    }

    console.log(this.chatForm.valid);
    console.log(this.chatForm.value);

    const question = this.chatForm.value.question?.trim();

    if (!question) {
      return;
    }

    // add user's message
    this.messages.push({
      sender: 'USER',
      message: question,
      timestamp: new Date(),
    });

    // clear input
    this.chatForm.reset();

    this.isThinking = true;

    // call backend
    this.chatService
      .chat(this.documentId, {
        question: question,
      })
      .subscribe({
        next: (response) => {
          this.isThinking = false;

          this.messages.push({
            sender: 'AI',
            message: response.answer,
            timestamp: new Date(),
          });

          this.cdr.detectChanges();
        },
        error: (error) => {
          this.isThinking = false;
          console.error(error);
        },
      });

    this.cdr.detectChanges();
  }

  openDocument(documentId:string): void {
    this.messages = [];
    this.router.navigate(['/chat', documentId]);
  }

  private addAskAiWelcomeMessage(): void {
    this.messages.push({
      sender: 'AI',
      message: 'Hi! 👋 I can help you understand your documents. Please select a document to start chatting.',
      timestamp: new Date()
    })
  }
}
