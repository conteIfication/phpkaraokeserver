@extends('layouts.admin-app')

@section('content')
    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a>
                </li>
                <li class="active">Profile</li>
            </ol>
        </div>
        <!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Profile</h1>
            </div>
        </div>
        <!--/.row-->
        <div>
            <div class="col-md-4">
                <div class="panel-layout">
                    <div class="panel-box">
                        <div class="panel-content image-box">
                            <div class="ribbon"></div>
                            <div class="image-content font-white">
                                <div class="meta-box meta-box-bottom">
                                    <img src="image/images.png" alt="" class="meta-image img-bordered img-circle">
                                    <h3 class="meta-heading">Alex Rosenberg</h3>
                                    <h4 class="meta-subheading">Ultimate backend programmer</h4>
                                </div>
                            </div>
                            <img src="../../assets/image-resources/blurred-bg/blurred-bg-13.jpg" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/.row-->
        <div class="col-md-8">
            <div class="example-box-wrapper">
                <div class="tab-content">
                    <div class="tab-pane fade" id="tab-example-1">
                        <div class="alert alert-close alert-success">
                            <a href="#" title="Close" class="glyph-icon alert-close-btn icon-remove"></a>
                            <div class="bg-green alert-icon"><i class="glyph-icon icon-check"></i></div>
                            <div class="alert-content">
                                <h4 class="alert-title">Example Infobox</h4>
                                <p>Lorem ipsum dolor sic amet dixit tu...</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="content-box">
                                    <form class="form-horizontal clearfix pad15L pad15R pad20B bordered-row">
                                        <div class="form-group remove-border">
                                            <label class="col-sm-7 control-label">Enable account:</label>
                                            <div class="col-sm-3">
                                                <input type="checkbox" class="input-switch-alt" style="display: none;">
                                                <div id="#undefined" class="switch-toggle" style="display: block;"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-7 control-label">Visible Profile</label>
                                            <div class="col-sm-3">
                                                <input type="checkbox" checked="checked" class="input-switch-alt" style="display: none;">
                                                <div id="#undefined" class="switch-toggle switch-on" style="display: block;"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-7 control-label">Hide timeline</label>
                                            <div class="col-sm-3">
                                                <div class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-off bootstrap-switch-small bootstrap-switch-animate">
                                                    <div class="bootstrap-switch-container"><span class="bootstrap-switch-handle-on bootstrap-switch-danger">On</span><label class="bootstrap-switch-label">&nbsp;</label><span class="bootstrap-switch-handle-off bootstrap-switch-default">Off</span><input type="checkbox" data-on-color="danger" data-size="small" name="checkbox-example-1" class="input-switch" data-on-text="On" data-off-text="Off"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-7 control-label">Is it active?</label>
                                            <div class="col-sm-3">
                                                <div class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-off bootstrap-switch-small bootstrap-switch-animate">
                                                    <div class="bootstrap-switch-container"><span class="bootstrap-switch-handle-on bootstrap-switch-info">On</span><label class="bootstrap-switch-label">&nbsp;</label><span class="bootstrap-switch-handle-off bootstrap-switch-default">Off</span><input type="checkbox" data-on-color="info" data-size="small" name="checkbox-example-2" class="input-switch" data-on-text="On" data-off-text="Off"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-7 control-label">Radio example</label>
                                            <div class="col-sm-3">
                                                <div class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-on bootstrap-switch-small bootstrap-switch-animate">
                                                    <div class="bootstrap-switch-container"><span class="bootstrap-switch-handle-on bootstrap-switch-success">On</span><label class="bootstrap-switch-label">&nbsp;</label><span class="bootstrap-switch-handle-off bootstrap-switch-default">Off</span><input type="checkbox" data-on-color="success" data-size="small" name="checkbox-example-3" class="input-switch" checked="checked" data-on-text="On" data-off-text="Off"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="content-box mrg15B">
                                    <h3 class="content-box-header clearfix">
                                        Change Password
                                        <div class="font-size-11 float-right"><a href="#" title=""><i class="glyph-icon mrg5R opacity-hover icon-plus"></i></a> <a href="#" title=""><i class="glyph-icon opacity-hover icon-cog"></i></a></div>
                                    </h3>
                                    <div class="content-box-wrapper pad0T clearfix">
                                        <form class="form-horizontal pad15L pad15R bordered-row">
                                            <div class="form-group">
                                                <label class="col-sm-6 control-label">Old password:</label>
                                                <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder=""></div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-6 control-label">New password:</label>
                                                <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder=""></div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-6 control-label">Repeat password:</label>
                                                <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder=""></div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="button-pane mrg20T"><button class="btn btn-success">Update Password</button></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="tab-example-2">
                        <div class="content-box pad25A">
                            <ul class="chat-box">
                                <li>
                                    <div class="chat-author"><img width="36" src="../../assets/image-resources/gravatar.jpg" alt=""></div>
                                    <div class="popover left no-shadow">
                                        <div class="arrow"></div>
                                        <div class="popover-content">
                                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio. Quisque volutpat mattis eros. Nullam malesuada erat ut turpis. Suspendisse urna nibh, viverra non, semper suscipit, posuere a, pede.
                                            <div class="chat-time"><i class="glyph-icon icon-clock-o"></i> a few seconds ago</div>
                                        </div>
                                    </div>
                                </li>
                                <li class="float-left">
                                    <div class="chat-author"><img width="36" src="../../assets/image-resources/gravatar.jpg" alt=""></div>
                                    <div class="popover right no-shadow">
                                        <div class="arrow"></div>
                                        <div class="popover-content">
                                            <h3>
                                                <a href="#" title="Horia Simon">Horia Simon</a>
                                                <div class="float-right"><a href="#" class="btn glyph-icon icon-inbox font-gray tooltip-button" data-placement="bottom" title="" data-original-title="This chat line was received in the mail."></a></div>
                                            </h3>
                                            This comment line has a title (author name) and a right button panel.
                                            <div class="chat-time"><i class="glyph-icon icon-clock-o"></i> a few seconds ago</div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="chat-author"><img width="36" src="../../assets/image-resources/gravatar.jpg" alt=""></div>
                                    <div class="popover left no-shadow">
                                        <div class="arrow"></div>
                                        <div class="popover-content">
                                            This comment line has a bottom button panel, box shadow and title.
                                            <div class="chat-time"><i class="glyph-icon icon-clock-o"></i> a few seconds ago</div>
                                            <div class="divider"></div>
                                            <a href="#" class="btn btn-sm btn-default font-bold text-transform-upr" title=""><span class="button-content">Reply</span></a> <a href="#" class="btn btn-sm btn-danger float-right tooltip-button" data-placement="left" title="" data-original-title="Remove comment"><i class="glyph-icon icon-remove"></i></a>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="chat-author"><img width="36" src="../../assets/image-resources/gravatar.jpg" alt=""></div>
                                    <div class="popover left no-shadow">
                                        <div class="arrow"></div>
                                        <div class="popover-content">
                                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio. Quisque volutpat mattis eros. Nullam malesuada erat ut turpis. Suspendisse urna nibh, viverra non, semper suscipit, posuere a, pede.
                                            <div class="chat-time"><i class="glyph-icon icon-clock-o"></i> a few seconds ago</div>
                                        </div>
                                    </div>
                                </li>
                                <li class="float-left">
                                    <div class="chat-author"><img width="36" src="../../assets/image-resources/gravatar.jpg" alt=""></div>
                                    <div class="popover right no-shadow">
                                        <div class="arrow"></div>
                                        <div class="popover-content">
                                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio. Quisque volutpat mattis eros. Nullam malesuada erat ut turpis. Suspendisse urna nibh, viverra non, semper suscipit, posuere a, pede.
                                            <div class="chat-time"><i class="glyph-icon icon-clock-o"></i> a few seconds ago</div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="tab-example-3">
                        <div class="row">
                            <div class="col-md-3">
                                <ul class="list-group">
                                    <li class="mrg10B"><a href="#faq-tab-1" data-toggle="tab" class="list-group-item">How to get paid <i class="glyph-icon icon-angle-right mrg0A"></i></a></li>
                                    <li class="mrg10B"><a href="#faq-tab-2" data-toggle="tab" class="list-group-item">ThemeForest related <i class="glyph-icon font-green icon-angle-right mrg0A"></i></a></li>
                                    <li class="mrg10B"><a href="#faq-tab-3" data-toggle="tab" class="list-group-item">Common questions <i class="glyph-icon icon-angle-right mrg0A"></i></a></li>
                                    <li class="mrg10B"><a href="#faq-tab-4" data-toggle="tab" class="list-group-item">Terms of service <i class="glyph-icon icon-angle-right mrg0A"></i></a></li>
                                </ul>
                            </div>
                            <div class="col-md-9">
                                <div class="tab-content">
                                    <div class="tab-pane fade active in pad0A" id="faq-tab-1">
                                        <div class="panel-group" id="accordion5">
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne">Collapsible Group Item #1</a></h4>
                                                </div>
                                                <div id="collapseOne" class="panel-collapse collapse in">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion5" href="#collapseTwo">Collapsible Group Item #2</a></h4>
                                                </div>
                                                <div id="collapseTwo" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion5" href="#collapseThree">Collapsible Group Item #3</a></h4>
                                                </div>
                                                <div id="collapseThree" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade pad0A" id="faq-tab-2">
                                        <div class="panel-group" id="accordion1">
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1">Collapsible Group Item #1</a></h4>
                                                </div>
                                                <div id="collapseOne1" class="panel-collapse collapse in">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion1" href="#collapseTwo1">Collapsible Group Item #2</a></h4>
                                                </div>
                                                <div id="collapseTwo1" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion1" href="#collapseThree1">Collapsible Group Item #3</a></h4>
                                                </div>
                                                <div id="collapseThree1" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade pad0A" id="faq-tab-3">
                                        <div class="panel-group" id="accordion2">
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2">Collapsible Group Item #1</a></h4>
                                                </div>
                                                <div id="collapseOne2" class="panel-collapse collapse in">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo2">Collapsible Group Item #2</a></h4>
                                                </div>
                                                <div id="collapseTwo2" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion2" href="#collapseThree2">Collapsible Group Item #3</a></h4>
                                                </div>
                                                <div id="collapseThree2" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade pad0A" id="faq-tab-4">
                                        <div class="panel-group" id="accordion3">
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne4">Collapsible Group Item #1</a></h4>
                                                </div>
                                                <div id="collapseOne4" class="panel-collapse collapse in">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo4">Collapsible Group Item #2</a></h4>
                                                </div>
                                                <div id="collapseTwo4" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                            <div class="panel">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseThree4">Collapsible Group Item #3</a></h4>
                                                </div>
                                                <div id="collapseThree4" class="panel-collapse collapse">
                                                    <div class="panel-body">Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane pad0A fade active in" id="tab-example-4">
                        <div class="content-box">
                            <form class="form-horizontal pad15L pad15R bordered-row">
                                <div class="form-group remove-border">
                                    <label class="col-sm-3 control-label">First Name:</label>
                                    <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder="First name..."></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Last Name:</label>
                                    <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder="Last name..."></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Email:</label>
                                    <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder="Email address..."></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Website:</label>
                                    <div class="col-sm-6"><input type="text" class="form-control" id="" placeholder="Website..."></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">About me:</label>
                                    <div class="col-sm-6"><textarea name="" rows="3" class="form-control textarea-autosize" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 78px;"></textarea></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"></label>
                                    <div class="col-sm-6"><button class="btn btn-info">Save</button></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.row-->
            <!--/.row-->
        </div>
@endsection
