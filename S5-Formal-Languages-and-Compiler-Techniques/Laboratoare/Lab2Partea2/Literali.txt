Integer literal
Allows values of integer type to be used in expressions directly.

Syntax
An integer literal has the form

decimal-literal integer-suffix(optional)	(1)
octal-literal integer-suffix(optional)	(2)
hex-literal integer-suffix(optional)	(3)
binary-literal integer-suffix(optional)	(4)	(since C++14)
where:

- decimal-literal is a non-zero decimal digit (1, 2, 3, 4, 5, 6, 7, 8, 9), followed by zero or more decimal digits
(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
- octal-literal is the digit zero (0) followed by zero or more octal digits (0, 1, 2, 3, 4, 5, 6, 7)
- hex-literal is the character sequence 0x or the character sequence 0X followed by one or more hexadecimal digits
(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, A, b, B, c, C, d, D, e, E, f, F)
- binary-literal is the character sequence 0b or the character sequence 0B followed by one or more binary digits (0, 1)
- integer-suffix, if provided, may contain one or both of the following (if both are provided, they may appear in any order:
    - unsigned-suffix (the character u or the character U)
    - one of
        - long-suffix (the character l or the character L)
        - long-long-suffix (the character sequence ll or the character sequence LL) (since C++11)
        - size-suffix (the character z or the character Z) (since C++23)

Optional single quotes (') may be inserted between the digits as a separator; they are ignored when determining the
value of the literal. (since C++14)

An integer literal (as any literal) is a primary expression.

Bibliography:
https://en.cppreference.com/w/cpp/language/integer_literal