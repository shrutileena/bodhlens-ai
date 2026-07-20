export interface ChatMessage {
    sender: 'USER' | 'AI';
    message: string;
    timestamp: Date;
}