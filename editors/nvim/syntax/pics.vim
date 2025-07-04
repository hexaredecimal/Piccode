" Vim syntax file
" Language:     PiccodeScript
" Filenames:    *.pics


" quit when a syntax file was already loaded
if exists("b:current_syntax")
  finish
endif

" Disable spell checking of syntax.
syn spell notoplevel

" pics is case sensitive.

" lowercase identifier - the standard way to match
" syn match    picsLCIdentifier /\<\(\l\|_\)\(\w\|'\)*\>/

syn match    picsKeyChar    "|"

" Some convenient clusters
syn cluster  picsAllErrs contains=picsBraceErr,picsBrackErr,picsParenErr,picsCommentErr,picsEndErr,picsThenErr

syn cluster  picsAENoParen contains=picsBraceErr,picsBrackErr,picsCommentErr,picsEndErr,picsThenErr

syn cluster  picsContained contains=picsTodo,picsPreDef,picsModParam,picsModParam1,picsPreMPRestr,picsMPRestr,picsMPRestr1,picsMPRestr2,picsMPRestr3,picsModRHS,picsFuncWith,picsFuncStruct,picsModTypeRestr,picsModTRWith,picsWith,picsWithRest,picsModType,picsFullMod


" Enclosing delimiters
syn region   picsEncl transparent matchgroup=picsKeyword start="(" matchgroup=picsKeyword end=")" contains=ALLBUT,@picsContained,picsParenErr
syn region   picsEncl transparent matchgroup=picsKeyword start="{" matchgroup=picsKeyword end="}"  contains=ALLBUT,@picsContained,picsBraceErr
syn region   picsEncl transparent matchgroup=picsKeyword start="\[" matchgroup=picsKeyword end="\]" contains=ALLBUT,@picsContained,picsBrackErr
syn region   picsEncl transparent matchgroup=picsKeyword start="#\[" matchgroup=picsKeyword end="\]" contains=ALLBUT,@picsContained,picsBrackErr


" Comments
syn region picsComment start="\/\/" end="$" contains=picsComment,picsTodo,@Spell
" syn region   picsComment start="\%%" contains=picsComment,picsTodo,@Spell
syn keyword  picsTodo contained TODO FIXME

syn keyword  picsKeyword  module when is if else import do catch return

syn keyword  picsBoolean      true false
syn match    picsConstructor  "(\s*)"
syn match    picsConstructor  "\[\s*\]"
syn match    picsConstructor  "#\[\s*\]"
syn match    picsConstructor  "\u\(\w\|'\)*\>"

syn match picsFnIdent "[a-zA-Z_][a-zA-Z0-9_]*\s*\ze("

" Module prefix
syn match    picsModPath      "\u\(\w\|'\)*\."he=e-1

syn match    picsCharacter    +#"\\""\|#"."\|#"\\\d\d\d"+
syn match    picsCharErr      +#"\\\d\d"\|#"\\\d"+
syn region   picsString       start=+"+ skip=+\\\\\|\\"+ end=+"+ contains=@Spell

syn match    picsFunDef       "=>"
syn match    picsOperator     "::"
syn match    picsAnyVar       "\<_\>"
syn match    picsKeyChar      "!"
syn match    picsKeyChar      ";"
syn match    picsKeyChar      "\*"
syn match    picsKeyChar      "="

syn match    picsNumber        "\<-\=\d\+\>"
syn match    picsNumber        "\<-\=0[x|X]\x\+\>"
syn match    picsReal          "\<-\=\d\+\.\d*\([eE][-+]\=\d\+\)\=[fl]\=\>"

" Synchronization
syn sync minlines=20
syn sync maxlines=500

hi def link picsComment      Comment

hi def link picsModPath      Include
hi def link picsModule       Include
hi def link picsModParam1    Include
hi def link picsModType      Include
hi def link picsMPRestr3     Include
hi def link picsFullMod      Include
hi def link picsModTypeRestr Include
hi def link picsWith         Include
hi def link picsMTDef        Include

hi def link picsConstructor  Constant

hi def link picsModPreRHS    Keyword
hi def link picsMPRestr2     Keyword
hi def link picsKeyword      Keyword
hi def link picsFunDef       Keyword
hi def link picsRefAssign    Keyword
hi def link picsKeyChar      Keyword
hi def link picsAnyVar       Keyword
hi def link picsTopStop      Keyword
hi def link picsOperator     Keyword
hi def link picsThread       Keyword

hi def link picsBoolean      Boolean
hi def link picsAtom         Boolean
hi def link picsCharacter    Character
hi def link picsNumber       Number
hi def link picsReal         Float
hi def link picsString       String
hi def link picsType         Type
hi def link picsTodo         Todo
hi def link picsEncl         Keyword
hi def link picsFnIdent      Function

let b:current_syntax = "pics"

" vim: ts=8
