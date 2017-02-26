lexer grammar YangLexer;

@header {
  package iwostaq.yppj.g;
}

// Comments

COMMENT_SINGLE
:
  '//' ~[\r\n]* -> skip
;

COMMENT_BLOCK
:
  S_BLOCK_COMMENT_START (.)*? S_BLOCK_COMMENT_END -> skip
;

// Tokens with a single character

S_COLON
:
  ':'
;

S_SEMICOLON
:
  ';'
;

S_LBR
:
  '{'
;

S_RBR
:
  '}'
;

S_PLUS
:
  '+'
;

// Keywords

KW_anyxml
:
  'anyxml'
;

KW_argument
:
  'argument'
;

KW_augment
:
  'augment'
;

KW_base
:
  'base'
;

KW_belongs_to
:
  'belongs-to'
;

KW_bit
:
  'bit'
;

KW_case
:
  'case'
;

KW_choice
:
  'choice'
;

KW_config
:
  'config'
;

KW_contact
:
  'contact'
;

KW_container
:
  'container'
;

KW_default
:
  'default'
;

KW_description
:
  'description'
;

KW_enum
:
  'enum'
;

KW_error_app_tag
:
  'error-app-tag'
;

KW_error_message
:
  'error-message'
;

KW_extension
:
  'extension'
;

KW_deviation
:
  'deviation'
;

KW_deviate
:
  'deviate'
;

KW_feature
:
  'feature'
;

KW_fraction_digits
:
  'fraction-digits'
;

KW_grouping
:
  'grouping'
;

KW_identity
:
  'identity'
;

KW_if_feature
:
  'if-feature'
;

KW_import
:
  'import'
;

KW_include
:
  'include'
;

KW_input
:
  'input'
;

KW_key
:
  'key'
;

KW_leaf
:
  'leaf'
;

KW_leaf_list
:
  'leaf-list'
;

KW_length
:
  'length'
;

KW_list
:
  'list'
;

KW_mandatory
:
  'mandatory'
;

KW_max_elements
:
  'max-elements'
;

KW_min_elements
:
  'min-elements'
;

KW_module
:
  'module'
;

KW_must
:
  'must'
;

KW_namespace
:
  'namespace'
;

KW_notification
:
  'notification'
;

KW_ordered_by
:
  'ordered-by'
;

KW_organization
:
  'organization'
;

KW_output
:
  'output'
;

KW_path
:
  'path'
;

KW_pattern
:
  'pattern'
;

KW_position
:
  'position'
;

KW_prefix
:
  'prefix'
;

KW_presence
:
  'presence'
;

KW_range
:
  'range'
;

KW_reference
:
  'reference'
;

KW_refine
:
  'refine'
;

KW_require_instance
:
  'require-instance'
;

KW_revision
:
  'revision'
;

KW_revision_date
:
  'revision-date'
;

KW_rpc
:
  'rpc'
;

KW_status
:
  'status'
;

KW_submodule
:
  'submodule'
;

KW_type
:
  'type'
;

KW_typedef
:
  'typedef'
;

KW_unique
:
  'unique'
;

KW_units
:
  'units'
;

KW_uses
:
  'uses'
;

KW_value
:
  'value'
;

KW_when
:
  'when'
;

KW_yang_version
:
  'yang-version'
;

KW_yin_element
:
  'yin-element'
;

/*
UNKNWON_STMT
:
  IDENTIFIER ':' IDENTIFIER
;
*/
IDENTIFIER
:
  (
    ALPHA
    | '_'
  )
  (
    ALPHA
    | DIGIT
    | '_'
    | '-'
    | '.'
  )*
;

// String

STRING
:
  (
    '"'
    (
      ~'"'
      | '\\"'
    )* '"'
    |
    (
      '\''
      (
        ~'\''
      )* '\''
    )
    |
    (
      ~( ' ' | '\t' | '\r' | '\n' | ':' | ';' | '{' | '}' | '+' )
    )+
  )
  {
  String s = getText();
  if (0 < s.length() && ( s.charAt(0) == '"' || s.charAt(0) == '\'' )) {
    s = s.substring(1, s.length() - 1);
    s = s.replace("\\\"", "\"");
  }
  setText(s);
}

;

// Fragments

fragment
S_BLOCK_COMMENT_START
:
  '/*'
;

fragment
S_BLOCK_COMMENT_END
:
  '*/'
;

fragment
ALPHA
:
  [a-zA-Z]
;

fragment
NON_ZERO_DIGIT
:
  [1-9]
;

fragment
DIGIT
:
  [0-9]
;

fragment
ID_FIRST
:
  [a-kn-wyzA-KN-WYZ_]
;

fragment
WSP
:
  (
    ' '
    | '\t'
  )
;

SEP
:
  (
    WSP
    | '\r'? '\n'
  )+ -> skip
;
