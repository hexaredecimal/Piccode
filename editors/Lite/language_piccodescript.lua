-- mod-version:3
local syntax = require "core.syntax"

syntax.add {
  files = { "%.pics$"},
  comment = "//",
  patterns = {
    { pattern = "//.-\n",               type = "comment"  },
    { pattern = { "/%*", "%*/" },       type = "comment"  },
    { pattern = { '"', '"', '\\' },     type = "string"   },
    { pattern = { "'", "'", '\\' },     type = "string"   },
    { pattern = "-?0x%x+",              type = "number"   },
    { pattern = "-?%d+[%d%.eE]*f?",     type = "number"   },
    { pattern = "-?%.?%d+f?",           type = "number"   },
    { pattern = "[%+%-=/%*%^%%<>!~|&]", type = "operator" },
    { pattern = "[%a_][%w_]*%f[(]",     type = "function" },
    { pattern = "[%a_][%w_]*",          type = "symbol"   },
  },
  symbols = {
    ["import"]         = "keyword",
    ["module"]         = "keyword",
    ["if"]             = "keyword",
    ["else"]           = "keyword",
    ["do"]             = "keyword",
    ["when"]           = "keyword",
    ["is"]             = "keyword",
    ["return"]         = "keyword",
    ["catch"]          = "keyword",
    ["let"]          	 = "keyword",
    ["in"]             = "keyword",
    ["await"]          = "keyword",
    ["true"]           = "literal",
    ["false"]          = "literal",
  },
}

