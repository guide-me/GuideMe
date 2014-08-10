grammar NyxScript;

guide
    : page*
    ;

page
    : must_not
    | must  
    | PAGE_ID 'page(' (page_option (',' page_option)*)? ')'
    ;

must_not
    : 'mustnot(' 'self:' PAGE_ID (',' 'action' INT ':' PAGE_ID)+ ')'
    ;

must
    : 'must(' 'self:' PAGE_ID (',' 'action' INT ':' PAGE_ID)+ ')'
    ;


page_option
    : text 
    | media_pic
    | hidden_sound
    | action_group
    | action
    ;

action
    : action_buttons
    | action_delay
    | action_go 
    | action_yn
    | action_set
    | action_unset
    ;
 
action_group  
    : action_prefix ('vert'|'horiz'|'mult') '(' (action ','?)* ')'
    ;

action_prefix 
    : 'action:'
    | 'instruc:'
    | 'e' INT ':'
    ;

action_set
    : action_prefix? 'set(' 'action' INT ':' PAGE_ID (',' 'action' INT ':' PAGE_ID)* ')'
    ;

action_unset
    : action_prefix? 'unset(' 'action' INT ':' PAGE_ID (',' 'action' INT ':' PAGE_ID)* ')'
    ;

text
    : 'text:' QUOTED_STRING
    ;

media_pic
    : 'media:pic(' 'id:' QUOTED_STRING ')'
    ;

hidden_sound
    : 'hidden:sound(' 'id:' QUOTED_STRING (',' 'loops:' INT)? ')'
    ;

action_buttons
    : action_prefix 'buttons(' (button ','?)+  ')'
    ;

button
    : ('target' INT) ':' (range | PAGE_ID) ',' ('cap' INT) ':' QUOTED_STRING
    ;

action_delay
    : action_prefix 'delay(' time ',' action_target (',' 'style:' DELAY_STYLE)? ')'
    ;

time
    : 'time:' 'random(' 'min:' INT TIME_UNIT? ',' 'max:' INT TIME_UNIT? ')'
    | 'time:' INT TIME_UNIT?
    ;

action_go
    : action_prefix 'go(' action_target ')'
    ;

action_yn
    : action_prefix 'yn(' yes_button ',' no_button ')'
    ;

yes_button
    : 'yes:' (range | PAGE_ID)
    ;

no_button
    : 'no:' (range | PAGE_ID)
    ;

action_target
    : 'target:' (range | PAGE_ID)
    ;

range
    : 'range(' 'from:' INT ',' 'to:' INT (',' 'prefix'? ':' QUOTED_STRING)? ')'
    ;

DELAY_STYLE   
    : 'hidden' | '\'hidden\''
    | 'secret' | '\'secret\''
    | 'normal' | '\'normal\''
    ;

TIME_UNIT
    : 'sec' 
    | 'min' 
    | 'hrs' 
    ;

QUOTED_STRING 
    : '"' .*? '"'
    | '\'' .*? '\''
    | '’' .*? '’'
    ;

PAGE_ID
    : [A-Za-z0-9]+ '#'
    ;

INT           
    : [0-9]+
    ;

NEWLINE
    : [\r\n]+ -> skip
    ;

WS
    : [ \t]+ -> skip
    ;