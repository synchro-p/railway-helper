module.exports = {
    tabWidth: 4,
    semi: true,
    singleQuote: true,
    trailingComma: 'es5',
    printWidth: 100,
    overrides: [
        {
            files: '**/*.{yml,yaml}',
            options: {
                tabWidth: 2,
                semi: false,
            },
        },
    ],
};
