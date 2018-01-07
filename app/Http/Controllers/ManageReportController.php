<?php

namespace App\Http\Controllers;

use App\ReportUserKs;
use App\ReportUserSr;
use Illuminate\Http\Request;
use Illuminate\Pagination\LengthAwarePaginator;

class ManageReportController extends Controller
{
    //
    public function index(Request $request){
        $reportKs = ReportUserKs::orderBy('created_at', 'desc')->get();
        $reportSr = ReportUserSr::orderBy('created_at', 'desc')->get();

        $reports = array();
        foreach ($reportSr as $item){
            $item->sharedrecord;
            $item->sharedrecord->karaoke;
            $item->user;
            array_push( $reports, $item );
        }

        foreach ($reportKs as $item){
            $item->karaoke;
            $item->user;
            array_push( $reports, $item );
        }

        //DESC
        $reports = array_reverse(array_sort($reports, function ($value) {
            return $value['created_at'];
        }));


        // Get current page form url e.x. &page=1
        $currentPage = LengthAwarePaginator::resolveCurrentPage();

        // Create a new Laravel collection from the array data
        $itemCollection = collect($reports);

        // Define how many items we want to be visible in each page
        $perPage = 10;

        // Slice the collection to get the items to display in current page
        $currentPageItems = $itemCollection->slice(($currentPage * $perPage) - $perPage, $perPage)->all();

        // Create our paginator and pass it to the view
        $paginatedItems= new LengthAwarePaginator($currentPageItems , count($itemCollection), $perPage);

        // set url path for generted links
        $paginatedItems->setPath($request->url());


        return view('manage-reports', ['reports' => $paginatedItems]);
    }
}
