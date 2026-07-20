import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Dashboard } from './components/dashboard/dashboard';
import { authGuard } from './guards/auth-guard';
import { UploadDocument } from './components/upload-document/upload-document';
import { Documents } from './components/documents/documents';
import { Chat } from './components/chat/chat';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'dashboard',
        component: Dashboard,
        canActivate: [authGuard]
    },
    {
        path: 'upload-document',
        component: UploadDocument,
        canActivate: [authGuard]
    },
    {
        path: 'documents',
        component: Documents,
        canActivate: [authGuard]
    },
    {
        path: 'chat/:documentId',
        component: Chat,
        canActivate: [authGuard]
    },
    {
        path: 'chat',
        component: Chat,
        canActivate: [authGuard]
    }
];
