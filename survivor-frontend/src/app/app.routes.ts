import { Routes } from '@angular/router';
import { CastawayDetail } from './features/roster/components/castaway-detail/castaway-detail';
import { CastawayRoster } from './features/roster/components/castaway-roster/castaway-roster';
import { SeasonPicker } from './features/roster/components/season-picker/season-picker';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'seasons',
        pathMatch: 'full'
    },
    {
        path: 'seasons',
        component: SeasonPicker,
    },
    {
        path: 'contestants/:id',
        component: CastawayDetail
    },
    {
        path: 'seasons/:seasonId',
        component: CastawayRoster
    },
    {
        path: '**',
        component: CastawayRoster
    },
    
];
