import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackButton } from '../../shared/back-button/back-button';
import { DocumentResponse } from '../../models/document-response';
import { DocumentService } from '../../services/document-service';
import { ChatMessage } from '../../models/chat-message';
import { DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ChatService } from '../../services/chat-service';
import { ChatSessionService } from '../../services/chat-session-service';
import { ChatSession } from '../../models/chat-session';

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

  private chatSessionService = inject(ChatSessionService);

  isThinking = false;

  sessions: ChatSession[] = [];

  selectedSession?: ChatSession;

  chatForm = this.fb.group({
    question: ['', Validators.required],
  });

  ngOnInit(): void {
    this.documentId = this.route.snapshot.paramMap.get('documentId') ?? undefined;

    if (this.documentId) {
      this.getDocumentDetails();
      this.loadSessions();
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
        // this.addWelcomeMessage();
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
    // if(!this.documentId){
    //   return;
    // }
    if(!this.selectedSession){
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
      .chat(this.selectedSession.id, {
        question: question,
      })
      .subscribe({
        next: (response) => {
          // console.log("answer - " + response.answer);
          
          this.isThinking = false;

          // this.messages.push({
          //   sender: 'AI',
          //   message: response.answer,
          //   timestamp: new Date(),
          // });

          this.loadMessages();

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

  readonly MAX_LENGTH = 300;

  isLongMessage(message: ChatMessage): boolean {
    return message.message.length > this.MAX_LENGTH;
  }

  toggleMessage(message: ChatMessage): void {
    message.expanded = !message.expanded;
  }

  loadSessions() {

    if(!this.documentId) {
      return;
    }

    this.chatSessionService.getSessions(this.documentId).subscribe({
      next: (sessions) => {
        this.sessions = sessions;

        if(sessions.length === 0) {
          this.createSession();
        } else {
          this.selectedSession = sessions[0];
          this.loadMessages();
        }
      },
      error: (error) => {
        console.error('Failed to load chat sessions');
      }
    });
  }

  createSession(): void {

    if(!this.documentId) {
      return;
    }

    this.chatSessionService.createSession(this.documentId).subscribe({
      next: (session) => {
        this.sessions.unshift(session);
        this.selectedSession = session;
        this.messages = [];
        this.addWelcomeMessage();
        this.chatForm.reset();
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  loadMessages(): void {

    if(!this.selectedSession){
      return;
    }

    this.chatService.getMessages(this.selectedSession.id).subscribe({
      next: (messages) => {
        this.messages = messages.map(message => ({
          ...message,
          timestamp: new Date(message.timestamp)
        }));

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  selectSession(session: ChatSession): void {
    this.selectedSession = session;
    this.loadMessages();
  }
}
