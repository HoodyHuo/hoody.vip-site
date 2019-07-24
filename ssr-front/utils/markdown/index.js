const marked = require('marked')
const DOMPurify = require('dompurify')

/**
 * 转换Markdown 文本 为html ,并对html过滤,防止XSS攻击
 * warring 目前仅评论是在前端运行,所以只有评论会清理
 * @param md
 * @returns {*}
 */
export const getMD2HtmlClean = (md) => {
  const dirtyHtml = marked(md)
  if (process.client) {
    return DOMPurify.sanitize(dirtyHtml)
  }
  return dirtyHtml
}
