import { Routes } from '@angular/router';
import { CastawayDetail } from './features/roster/components/castaway-detail/castaway-detail';
import { CastawayRoster } from './features/roster/components/castaway-roster/castaway-roster';

export const routes: Routes = [
    {
        path: '',
        component: CastawayRoster
    },
    {
        path: 'contestants/:id',
        component: CastawayDetail
    },
    {
        path: '**',
        component: CastawayRoster
    }
];
