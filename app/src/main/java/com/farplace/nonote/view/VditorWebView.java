package com.farplace.nonote.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VditorWebView extends WebView {
    private static final String TAG = "----AME-----";
    private static final String SETUP_HTML = "file:///android_asset/editor/index.html";
    private OnGetHtmlResult onGetHtmlResult;
    private OnHtml2mdResult onHtml2mdResult;


    public VditorWebView(Context context) {
        this(context, null);
    }

    public VditorWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public VditorWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "-------");
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient());
//        addJavascriptInterface(new JsInterface(this),"ameBridge");
        loadUrl(SETUP_HTML);

    }

    //获取编辑器内容
    public void getValue(ValueCallback<String> callback) {
        evaluateJavascript("javascript:ameGetValue()", callback);
    }

    //在焦点处插入内容
    public void insertValue(String value, boolean render) {
        String script = "javascript:ameInsertValue('" + value + "','"+render+"')";
        evaluateJavascript(script, null);
    }

    //聚焦到编辑器
    public void focus() {
        evaluateJavascript("javascript:ameFocus()", null);
    }

    //失焦
    public void blur(){
        evaluateJavascript("javascript:ameBlur()", null);
    }

    //禁用
    public void disable(){
        evaluateJavascript("javascript:ameDisabled()", null);
    }

    //解除编辑器禁用
    public void enable(){
        evaluateJavascript("javascript:ameEnable()", null);
    }

    //选中从 start 开始到 end 结束的字符串
    public void setSelection(int start,int end){
        String script = "javascript:ameSetSelection(" + start + ","+end+")";
        evaluateJavascript(script,null);
    }

    //返回选中的字符串
    public void getSelection(ValueCallback<String> callback){
        evaluateJavascript("javascript:ameGetSelection()", callback);
    }

    //设置编辑器内容
    public void setValue(String value){
        String script = "javascript:ameSetValue('" + value + "')";
        evaluateJavascript(script,null);
    }

    //获取焦点位置
    public void getCursorPosition(ValueCallback<String> callback){
        evaluateJavascript("javascript:ameGetCursorPosition()", callback);
    }

    //删除选中内容
    public void deleteValue(){
        evaluateJavascript("javascript:ameDeleteValue()", null);
    }

    //更新选中内容
    public void updateValue(String value){
        String script = "javascript:ameUpdateValue('" + value + "')";
        evaluateJavascript(script,null);
    }

    //清除缓存
    public void clearCache(){
        evaluateJavascript("javascript:ameClearCache()", null);
    }

    //禁用缓存
    public void disabledCache(){
        evaluateJavascript("javascript:ameDisabledCache()", null);
    }

    //启用缓存
    public void enableCache(){
        evaluateJavascript("javascript:ameEnableCache()", null);
    }

    //设置预览模式
    public void setPreviewMode(PreviewMode previewMode){
        Log.e(TAG, "setPreviewMode: "+ previewMode);
        evaluateJavascript("javascript:ameSetPreviewMode('"+ previewMode+"')", null);
    }

    //设置模式
    public void setMode(Mode mode){
        evaluateJavascript("javascript:ameSetWysiwyg('"+ mode+"')", null);
    }

    //消息提示
    public void tip(String text,int time){
        String script = "javascript:ameTip('" + text + "',"+time+")";
        evaluateJavascript(script,null);
    }

    public void undo(){
        evaluateJavascript("javascript:ameUndo()", null);
    }

    public void redo(){
        evaluateJavascript("javascript:ameRedo()", null);
    }


    public void setBold(){
        evaluateJavascript("javascript:ameSetBold()", null);
    }

    public void setH1(){
        evaluateJavascript("javascript:ameSetH1()", null);
    }

    public void setH2(){
        evaluateJavascript("javascript:ameSetH2()", null);
    }

    public void setH3(){
        evaluateJavascript("javascript:ameSetH3()", null);
    }

    public void setH4(){
        evaluateJavascript("javascript:ameSetH4()", null);
    }

    public void setH5(){
        evaluateJavascript("javascript:ameSetH5()", null);
    }

    public void setH6(){
        evaluateJavascript("javascript:ameSetH6()", null);
    }

    public void setItalic(){
        evaluateJavascript("javascript:ameSetItalic()", null);
    }

    public void setStrike(){
        evaluateJavascript("javascript:ameSetStrike()", null);
    }

    public void setLine(){
        evaluateJavascript("javascript:ameSetLine()", null);
    }

    public void setQuote(){
        evaluateJavascript("javascript:ameSetQuote()", null);
    }

    public void setList(){
        evaluateJavascript("javascript:ameSetList()", null);
    }

    public void setOrdered(){
        evaluateJavascript("javascript:ameSetOrdered()", null);
    }

    public void setCheck(){
        evaluateJavascript("javascript:ameSetCheck()", null);
    }

    public void setCode(){
        evaluateJavascript("javascript:ameSetCode()", null);
    }

    public void setInlineCode(){
        evaluateJavascript("javascript:ameSetInlineCode()", null);
    }

    public void setLink(){
        evaluateJavascript("javascript:ameSetLink()", null);
    }

    public void setTable(){
        evaluateJavascript("javascript:ameSetTable()", null);
    }

    public void getHtml(ValueCallback<String> callback){
        evaluateJavascript("javascript:ameGetHtml()", callback);
    }

    public void html2md(String value,ValueCallback<String> callback){
        String script = "javascript:ameHtml2md('" + value + "')";
        evaluateJavascript(script,callback);
    }


    @Deprecated
    public void getHtml(OnGetHtmlResult onGetHtmlResult){
        this.onGetHtmlResult = onGetHtmlResult;
        evaluateJavascript("javascript:ameGetHtml()", null);
    }

    @Deprecated
    public void html2md(String value,OnHtml2mdResult onHtml2mdResult){
        this.onHtml2mdResult = onHtml2mdResult;
        String script = "javascript:ameHtml2md('" + value + "')";
        evaluateJavascript(script,null);
    }

//    public void setOnGetHtmlResult(OnGetHtmlResult onGetHtmlResult){
//        this.onGetHtmlResult = onGetHtmlResult;
//    }

    @Deprecated
    protected void getHtmlFinished(String result){
        if(this.onGetHtmlResult!=null){
            this.onGetHtmlResult.onGetHtmlResult(result);
        }
    }

    @Deprecated
    protected void html2mdFinished(String result){
        if(this.onHtml2mdResult!=null){
            this.onHtml2mdResult.onHtml2mdResult(result);
        }
    }

    @Deprecated
    public interface OnGetHtmlResult{
        void onGetHtmlResult(String result);
    }

    @Deprecated
    public interface OnHtml2mdResult{
        void onHtml2mdResult(String result);
    }

    public enum PreviewMode {
        both,editor,preview
    }

    public enum Mode {
        wysiwyg,markdown
    }



}