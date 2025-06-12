-- Zulu neovim plugin

local function setup()
	vim.cmd([[
        autocmd BufRead,BufNewFile *.pics set filetype=pics
        autocmd Syntax pics runtime! syntax/pics.vim
    ]])
end

return {
	setup = setup,
}
